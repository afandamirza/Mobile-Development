package com.bangkit.recyclopedia.view.takephoto

import android.Manifest
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.bangkit.recyclopedia.R
import com.bangkit.recyclopedia.databinding.ActivityTakePhotoBinding
import com.bangkit.recyclopedia.api.ApiConfig
import com.bangkit.recyclopedia.api.response.ImagePredictionResponse
import com.bangkit.recyclopedia.data.pref.UserPreference
import com.bangkit.recyclopedia.data.pref.dataStore
import com.bangkit.recyclopedia.view.ViewModelFactory
import com.bangkit.recyclopedia.view.classification.ClassificationResultActivity
import com.bangkit.recyclopedia.view.classification.FailedResultActivity
import com.bangkit.recyclopedia.view.homepage.HomeActivity
import com.bangkit.recyclopedia.view.homepage.HomeActivity.Companion.REQUEST_CODE_PICK_IMAGE
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TakePhotoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTakePhotoBinding

    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var takePhotoViewModel: TakePhotoViewModel
    private val uiState: MutableLiveData<TakePhotoUiState> = MutableLiveData(TakePhotoUiState.Idle)

    private val _finishingActivity = MutableLiveData<Boolean>()

    private var isTrash: Boolean = false
    private var imageUri: Uri? = null
    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions())
        { permissions ->
            // Handle Permission granted/rejected
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in REQUIRED_PERMISSIONS && !it.value)
                    permissionGranted = false
            }
            if (!permissionGranted) {
                Toast.makeText(baseContext,
                    "Permission request denied",
                    Toast.LENGTH_SHORT).show()
            } else {
                startCamera()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTakePhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cameraExecutor = Executors.newSingleThreadExecutor()
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            requestPermissions()
        }

        setupViewModel()
        initView()
        observeUiState()
    }

    private fun setupViewModel() {
        takePhotoViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore), this )
        )[TakePhotoViewModel::class.java]

    }

    private fun initView() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        isTrash = intent.getBooleanExtra(EXTRA_IS_TRASH, true)
        if (isTrash) {
            binding.takePhotoText.text = "Please take a photo or upload from gallery of your trash here to go to the next step"
        } else {
            binding.takePhotoText.text = "Please take a photo or upload from the gallery your recycled waste creation"
        }

        binding.btnCapture.setOnClickListener {
            takePhoto()
        }

        binding.btnStartGuessing.setOnClickListener {
            startGuessing()
        }

        binding.btnGallery.setOnClickListener {
            openGalleryForImage()
        }

        binding.backButton.setOnClickListener {
            finish()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.backHome.setOnClickListener {
            finish()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.titleRetakePhoto.setOnClickListener {
            finish()
            val intent = Intent(this, TakePhotoActivity::class.java)
            startActivity(intent)
        }


    }

    private fun observeUiState() {
        uiState.observe(this) { uiState ->
            binding.titleRetakePhoto.isVisible = (uiState is TakePhotoUiState.Idle).not()
            binding.layoutTakePhoto.isVisible = uiState is TakePhotoUiState.Idle
            binding.btnStartGuessing.isVisible = (uiState is TakePhotoUiState.StartGuessing)
            binding.layoutClaimPoint.isVisible = (uiState is TakePhotoUiState.ClaimPoint)
            binding.ivPreview.isVisible = (uiState is TakePhotoUiState.StartGuessing || uiState is TakePhotoUiState.ClaimPoint || uiState is TakePhotoUiState.Uploading)
            binding.cameraPreview.isVisible = uiState is TakePhotoUiState.Idle
            binding.progressBar.isVisible = (uiState is TakePhotoUiState.Uploading)
            binding.progressTextMain.isVisible = (uiState is TakePhotoUiState.Uploading)

        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.surfaceProvider = binding.cameraPreview.surfaceProvider
                }
            imageCapture = ImageCapture.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture)

            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun requestPermissions() {
        activityResultLauncher.launch(REQUIRED_PERMISSIONS)
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(contentResolver, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            .build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }


                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = output.savedUri
                    if (savedUri != null) {
                        Log.d(TAG, "Photo capture succeeded: $savedUri")
                        binding.ivPreview.setImageURI(savedUri)
                        imageUri = savedUri // Save the image URI
                        uiState.value = TakePhotoUiState.StartGuessing // Show "Start Guessing" button
                    } else {
                        Log.e(TAG, "Photo capture failed: savedUri is null")
                        // Handle the error scenario, e.g., show a message to the user
                    }
                }
            }
        )
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK) {
            data?.data?.let { imageUri ->
                binding.ivPreview.setImageURI(imageUri)
                this.imageUri = imageUri
                uiState.value = TakePhotoUiState.StartGuessing // Show "Start Guessing" button
            }
        }
    }

    private fun startGuessing() {
        Log.d(TAG, "Start Guessing button clicked")
        imageUri?.let {
            Log.d(TAG, "Image URI found: $it")
            uiState.value = TakePhotoUiState.Uploading
            uploadImage(it)
        } ?: Log.e(TAG, "No image to upload")
    }

    private fun uploadImage(imageUri: Uri) {
        Log.d(TAG, "Uploading image: $imageUri")
        val filePath = getRealPathFromURI(imageUri)
        if (filePath == null) {
            Log.e(TAG, "Failed to get real path from URI")
            Toast.makeText(this, "Failed to get image path", Toast.LENGTH_SHORT).show()
            return
        }

        val file = File(filePath)

        val requestFile: RequestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val body: MultipartBody.Part = MultipartBody.Part.createFormData("image", file.name, requestFile)
        val description: RequestBody = RequestBody.create(MultipartBody.FORM, "image description")

        val apiService = ApiConfig.getApiService()
        val call = apiService.uploadImage(body, description)

        call.enqueue(object : Callback<ImagePredictionResponse> {
            override fun onResponse(call: Call<ImagePredictionResponse>, response: Response<ImagePredictionResponse>) {
                if (response.isSuccessful) {
                    val predictionResponse = response.body()
                    Log.d(TAG, "Upload succeeded: ${predictionResponse?.data?.result}")

                    when (predictionResponse?.status) {
                        "success" -> {
                            Log.d(TAG, "Prediction succeeded")
                            val intent = Intent(this@TakePhotoActivity, ClassificationResultActivity::class.java)
                            intent.putExtra("result", predictionResponse.data?.result)
                            intent.putExtra("confidenceScore", predictionResponse.data?.confidenceScore)
                            startActivity(intent)
                            finish()
                        }
                        "error" -> {
                            Log.e(TAG, "Prediction error: ${predictionResponse.message}")
                            val intent = Intent(this@TakePhotoActivity, ClassificationResultActivity::class.java)
                            intent.putExtra("message", predictionResponse.message)
                            startActivity(intent)
                            finish()
                        }
                        else -> {
                            Log.e(TAG, "Unexpected status: ${predictionResponse?.status}")
                            val intent = Intent(this@TakePhotoActivity, FailedResultActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    Log.e(TAG, "Upload failed: ${response.message()}")
                    val intent = Intent(this@TakePhotoActivity, FailedResultActivity::class.java)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this@TakePhotoActivity, "Upload failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ImagePredictionResponse>, t: Throwable) {
                Log.e(TAG, "Upload failed: ${t.message}", t)
                Toast.makeText(this@TakePhotoActivity, "Upload failed: ${t.message}", Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }

    private fun getRealPathFromURI(contentUri: Uri?): String? {
        if (contentUri == null) return null

        var cursor = contentResolver.query(contentUri, null, null, null, null)
        return if (cursor == null) {
            contentUri.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            cursor.getString(idx).also { cursor.close() }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        const val EXTRA_IS_TRASH = "extra_is_trash"
        private val REQUIRED_PERMISSIONS =
            mutableListOf (
                Manifest.permission.CAMERA
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}
