package com.example.github_user_application.data.di

import android.content.Context
import com.example.github_user_application.data.repository.FavoriteRepository
import com.example.github_user_application.data.local.room.FavoriteDatabase
import com.example.github_user_application.data.remote.retrofit.ApiConfig
import com.example.github_user_application.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): FavoriteRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteDatabase.getInstance(context)
        val dao = database.favoriteDao()
        val appExecutors = AppExecutors()
        return FavoriteRepository.getInstance(apiService, dao, appExecutors)
    }
}