package com.bangkit.recyclopedia.view.take_photo

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import com.bangkit.recyclopedia.R
import com.bangkit.recyclopedia.databinding.ActivityTakePhotoBinding
import com.bangkit.recyclopedia.view.description.DescriptionActivity
import com.bangkit.recyclopedia.view.description.TransactionActivity
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TakePhotoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTakePhotoBinding

    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService
    private val uiState: MutableLiveData<TakePhotoUiState> = MutableLiveData(TakePhotoUiState.Idle)

    private var isTrash: Boolean = false
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

        initView()
        observeUiState()
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
            val intent = Intent(this, DescriptionActivity::class.java)
            startActivity(intent)
        }

        binding.btnClaimPoint.setOnClickListener {
            val intent = Intent(this@TakePhotoActivity, TransactionActivity::class.java)
            intent.putExtra(TransactionActivity.EXTRA_IS_CLAIM_POINT, true)
            startActivity(intent)
        }

        binding.btnContinueLater.setOnClickListener {
            val intent = Intent(this@TakePhotoActivity, TransactionActivity::class.java)
            intent.putExtra(TransactionActivity.EXTRA_IS_CLAIM_POINT, false)
            startActivity(intent)
        }
    }

    private fun observeUiState() {
        uiState.observe(this) { uiState ->
            binding.titleTakePhoto.isVisible = (uiState is TakePhotoUiState.Idle).not()
            binding.layoutTakePhoto.isVisible = uiState is TakePhotoUiState.Idle
            binding.btnStartGuessing.isVisible = (uiState is TakePhotoUiState.StartGuessing)
            binding.layoutClaimPoint.isVisible = (uiState is TakePhotoUiState.ClaimPoint)
            binding.ivPreview.isVisible = (uiState is TakePhotoUiState.ClaimPoint || uiState is TakePhotoUiState.StartGuessing)
            binding.cameraPreview.isVisible = uiState is TakePhotoUiState.Idle
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
        Log.i(TAG, "Take photo")
        val imageCapture = imageCapture ?: return
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues)
            .build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults){
                    Log.d(TAG, "Photo capture succeeded: ${output.savedUri}")
                    binding.ivPreview.setImageURI(output.savedUri)
                    if (isTrash) {
                        uiState.value = TakePhotoUiState.StartGuessing
                    } else {
                        uiState.value = TakePhotoUiState.ClaimPoint
                    }
                }
            }
        )
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