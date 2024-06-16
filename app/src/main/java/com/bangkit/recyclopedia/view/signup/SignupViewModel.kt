package com.bangkit.recyclopedia.view.signup

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.recyclopedia.api.ApiConfig.getApiService
import com.bangkit.recyclopedia.api.response.FileUploadResponse
import com.bangkit.recyclopedia.data.model.UserSignUpModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewModel : ViewModel() {
    private val _finishingActivity = MutableLiveData<Unit>()
    val finishActivity: LiveData<Unit> = _finishingActivity

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun signupUser(user: UserSignUpModel, context: Context) {
        _isLoading.value = false
        val dataClient = getApiService().signupUser(user)
        dataClient.enqueue(object : Callback<FileUploadResponse> {
            override fun onResponse(
                call: Call<FileUploadResponse>,
                response: Response<FileUploadResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null && !responseBody.error) {
                        showAlertDialog("Berhasil!", "Akunmu berhasil dibuat!. Silahkan Login untuk masuk ke dalam aplikasi.", context)
                    }
                } else {
                    showAlertDialog("Pendaftaran gagal", response.message(), context)
                }

            }

            override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {
                _isLoading.value = false
                showAlertDialog("Pendaftaran Gagal", t.message, context)
            }
        })

    }

    private fun showAlertDialog (title: String, message: String?, context: Context){
        AlertDialog.Builder(context).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton("Lanjut") { _, _ ->
                _finishingActivity.value = Unit
            }
            setCancelable(false)
            create()
            show()
        }
    }
}