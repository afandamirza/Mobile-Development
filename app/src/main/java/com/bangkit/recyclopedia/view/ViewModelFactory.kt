package com.bangkit.recyclopedia.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.recyclopedia.data.pref.UserPreference
import com.bangkit.recyclopedia.di.Injection
import com.bangkit.recyclopedia.view.history.HistoryViewModel
import com.bangkit.recyclopedia.view.login.LoginViewModel
import com.bangkit.recyclopedia.view.main.MainViewModel
import com.bangkit.recyclopedia.view.profile.ProfileViewModel
import com.bangkit.recyclopedia.view.signup.SignupViewModel
import com.bangkit.recyclopedia.view.takephoto.TakePhotoViewModel

class ViewModelFactory(
    private val preference: UserPreference, private val context: Context) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(preference) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(preference) as T
            }
            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel() as T
            }

            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(preference) as T
            }
            modelClass.isAssignableFrom(TakePhotoViewModel::class.java) -> {
                TakePhotoViewModel(preference) as T
            }
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(preference, Injection.provideRepository()) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}