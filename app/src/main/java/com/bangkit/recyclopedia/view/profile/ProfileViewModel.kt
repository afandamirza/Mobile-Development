package com.bangkit.recyclopedia.view.profile

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.recyclopedia.api.ApiConfig
import com.bangkit.recyclopedia.api.response.ResetPasswordResponse
import com.bangkit.recyclopedia.data.model.UserModel
import com.bangkit.recyclopedia.data.model.UserResetPasswordModel
import com.bangkit.recyclopedia.data.pref.UserPreference
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel (private val preference: UserPreference)  : ViewModel() {

    private val _finishingActivity = MutableLiveData<Boolean>()
    val finishActivity: LiveData<Boolean> = _finishingActivity

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun resetPassword(user: UserResetPasswordModel, context: Context) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().resetPassword(user)
        client.enqueue(object : Callback<ResetPasswordResponse> {

            override fun onResponse(call: Call<ResetPasswordResponse>, response: Response<ResetPasswordResponse>) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        showAlertDialog("Permintaan Reset Password Berhasil dikirim!", "Cek emailmu untuk merubah password!", "Lanjutkan", context)
                    }
                } else {
                    var posButtonClicked = false
                    AlertDialog.Builder(context).apply {
                        setTitle("Aktifitas Gagal!")
                        setMessage("Isi Email telebih dahulu ${response.message()}")
                        setPositiveButton("Lanjutkan") { _, _ ->
                            if (posButtonClicked){
                                _finishingActivity.value = true
                            }
                        }
                        create()
                        show()
                    }
                }
            }

            override fun onFailure(call: Call<ResetPasswordResponse>, t: Throwable) {
                _isLoading.value = false
                showAlertDialog("Aktifitas Gagal!", "Tolong coba lagi", "Lanjutkan", context)
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

    fun getUser(): LiveData<UserModel> {
        return preference.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            preference.logout()
        }
    }
}