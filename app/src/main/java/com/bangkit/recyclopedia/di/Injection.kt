package com.bangkit.recyclopedia.di

import AppDatabase
import HistoryRepository
import android.content.Context
import androidx.room.Room
import com.bangkit.recyclopedia.api.ApiConfig

object Injection {
    fun provideRepository(): HistoryRepository {
        val apiService = ApiConfig.getApiService()
        return HistoryRepository.getInstance(apiService)
    }
}