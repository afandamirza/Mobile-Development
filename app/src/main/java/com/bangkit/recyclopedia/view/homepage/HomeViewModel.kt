//package com.bangkit.recyclopedia.view.homepage
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.asLiveData
//import androidx.lifecycle.viewModelScope
//import com.bangkit.recyclopedia.data.model.UserModel
//import com.bangkit.recyclopedia.data.pref.UserPreference
//import kotlinx.coroutines.launch
//
//class HomeViewModel (private val preference: UserPreference?) : ViewModel() {
//
//    fun getUser(): LiveData<UserModel>? {
//        return preference?.getUser()?.asLiveData()
//    }
//
//    fun logout() {
//        viewModelScope.launch {
//            preference?.logout()
//        }
//    }
//}