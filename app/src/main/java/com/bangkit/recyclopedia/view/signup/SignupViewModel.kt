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
    private val _finishingActivity = MutableLiveData<Boolean>()
    val finishActivity: LiveData<Boolean> = _finishingActivity

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun signupUser(user: UserSignUpModel, context: Context) {
        _isLoading.value = true
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
                        showAlertDialog("Success!", "Your account has been successfully created! Check your email first for authentication confirmation.", context)
                    }
                } else {
                    var posButtonClicked = false
                    AlertDialog.Builder(context).apply {
                        setTitle("Register Failed")
                        setMessage(response.message())
                        setPositiveButton("Continue") { _, _ ->
                            if (posButtonClicked){
                                _finishingActivity.value = true
                            }
                        }
                        create()
                        show()
                    }
                }

            }

            override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {
                _isLoading.value = false
                showAlertDialog("Register failed!", t.message, context)
            }
        })

    }

    private fun showAlertDialog (title: String, message: String?, context: Context){
        AlertDialog.Builder(context).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton("Continue") { _, _ ->
                _finishingActivity.value = true
            }
            setCancelable(false)
            create()
            show()
        }
    }
}