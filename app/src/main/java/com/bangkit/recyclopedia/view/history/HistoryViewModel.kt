// HistoryViewModel.kt
package com.bangkit.recyclopedia.view.history

import HistoryRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bangkit.recyclopedia.api.response.HistoryItemsResponse
import com.bangkit.recyclopedia.api.response.HistoryResponse
import com.bangkit.recyclopedia.data.model.UserModel
import com.bangkit.recyclopedia.data.pref.UserPreference
import kotlinx.coroutines.launch

class HistoryViewModel(private val preference: UserPreference, private val provideRepository: HistoryRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllStories(): LiveData<PagingData<HistoryItemsResponse>> =
        provideRepository.getStory().cachedIn(viewModelScope)

    fun getUser(): LiveData<UserModel> {
        return preference.getUser().asLiveData()
    }

}