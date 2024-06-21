package com.bangkit.recyclopedia.view.takephoto

import android.Manifest
import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.camera.core.CameraSelector
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.recyclopedia.api.ApiConfig
import com.bangkit.recyclopedia.api.ApiConfig.getApiService
import com.bangkit.recyclopedia.api.response.FileUploadResponse
import com.bangkit.recyclopedia.api.response.ImagePredictionResponse
import com.bangkit.recyclopedia.data.model.UserModel
import com.bangkit.recyclopedia.data.pref.UserPreference
import com.bangkit.recyclopedia.view.homepage.HomeActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class TakePhotoViewModel(private val preference: UserPreference): ViewModel() {
    private val _finishingActivity = MutableLiveData<Boolean>()

    private val _isLoading = MutableLiveData<Boolean>()
    fun getUser(): LiveData<UserModel> {
        return preference.getUser().asLiveData()
    }

    fun uploadImage(imageMultipart: MultipartBody.Part, context: Context, description: RequestBody,) {
        _isLoading.value = true


        val client = getApiService().uploadImage(imageMultipart, description)
        client.enqueue(object : Callback<ImagePredictionResponse> {

            override fun onResponse(call: Call<ImagePredictionResponse>, response: Response<ImagePredictionResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && responseBody.status != "error") {
                        showAlertDialog("Upload Story Berhasil!", "Story-mu berhasil diposting! Pergi ke halaman utama untuk melihat Story.", "Oke", context)
                    }
                } else {
                    showAlertDialog("Upload Gagal", response.message(), "Oke", context)

                }
            }

            override fun onFailure(call: Call<ImagePredictionResponse>, t: Throwable) {
                _isLoading.value = false
                showAlertDialog("Upload Gagal", t.message, "Oke", context)
            }
        })
    }



    private fun showAlertDialog(title: String, message: String?, posButton: String, context: Context) {
        AlertDialog.Builder(context).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton(posButton) { _, _ ->
                _finishingActivity.value = true
            }
            setCancelable(false)
            create()
            show()
        }
    }
}