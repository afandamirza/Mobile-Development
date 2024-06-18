package com.bangkit.recyclopedia.view.homepage

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.recyclopedia.R
import com.bangkit.recyclopedia.api.ApiConfig
import com.bangkit.recyclopedia.api.response.ImagePredictionResponse
import com.bangkit.recyclopedia.data.pref.UserPreference
import com.bangkit.recyclopedia.data.pref.dataStore
import com.bangkit.recyclopedia.databinding.ActivityHomeBinding
import com.bangkit.recyclopedia.view.ViewModelFactory
import com.bangkit.recyclopedia.view.welcome.WelcomePageActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeViewModel: HomeViewModel


    companion object {
        const val REQUEST_CODE_PICK_IMAGE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()

        // Set onClick listener for FloatingActionButton
        binding.fabAddStoryButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
        }


        val items = listOf(
            MyItem(R.drawable.img, "Cardboard", "Cardboard is a type of waste that comes from thick and strong paper. So that nature can decompose or destroy..."),
            MyItem(R.drawable.img, "Paper", "Paper is a type of waste that comes from wood fiber or other organic materials which are processed into thin sheets. Paper is very commonly used in everyday life..."),
            MyItem(R.drawable.img, "Glass Bottle", "Glass bottles are a type of waste that comes from glass which is produced by melting sand, sodium carbonate and limestone. Glass is very commonly used..."),
            MyItem(R.drawable.img, "Plastic Bottle", "Plastic bottles are a type of waste that comes from synthetic polymer materials such as PET (polyethylene terephthalate), HDPE (high-density polyethylene), and other types of plastic..."),
            MyItem(R.drawable.img, "Can", "Beverage cans are a type of waste that comes from aluminum or other metals. Cans are widely used for packaging drinks and food...")
        )


        val adapter = HomeAdapter(items)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }



    private fun setupViewModel() {
        homeViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore), this)
        )[HomeViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                AlertDialog.Builder(this).apply {
                    setTitle("Logout Confirmation")
                    setMessage("Are you sure you want to logout from your account?")
                    setPositiveButton("Yes") { _, _ ->
                        homeViewModel.logout()
                        val intent = Intent(context, WelcomePageActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                    setNegativeButton("No") { _, _ -> }
                    create()
                    show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this).apply {
            setTitle("Exit Application")
            setMessage("Are you sure you want to exit the application?")
            setPositiveButton("Yes") { _, _ ->
                super.onBackPressed()
                finishAffinity()
            }
            setNegativeButton("No") { _, _ -> }
            create()
            show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            val selectedImageUri = data?.data
            selectedImageUri?.let {
                uploadImageToServer(it)
            }
        }
    }

    private fun uploadImageToServer(fileUri: Uri) {
        val file = File(getRealPathFromURI(fileUri))
        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
        val description = RequestBody.create("text/plain".toMediaTypeOrNull(), "image description")

        val call = ApiConfig.getApiService().uploadImage(body, description)
        call.enqueue(object : Callback<ImagePredictionResponse> {
            override fun onResponse(call: Call<ImagePredictionResponse>, response: Response<ImagePredictionResponse>) {
                if (response.isSuccessful) {
                    val predictionResponse = response.body()
                    predictionResponse?.let {
                        Toast.makeText(this@HomeActivity, it.message, Toast.LENGTH_SHORT).show()
                        // Display prediction result or handle it as needed
                    }
                } else {
                    Toast.makeText(this@HomeActivity, "Prediction failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ImagePredictionResponse>, t: Throwable) {
                Toast.makeText(this@HomeActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getRealPathFromURI(uri: Uri): String {
        var path = ""
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                path = it.getString(columnIndex)
            }
        }
        return path
    }
}