package com.bangkit.recyclopedia.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.recyclopedia.data.model.UserModel
import com.bangkit.recyclopedia.data.pref.UserPreference

class MainViewModel (private val preference: UserPreference) : ViewModel() {
    fun getUser(): LiveData<UserModel> {
        return preference.getUser().asLiveData()
    }
}