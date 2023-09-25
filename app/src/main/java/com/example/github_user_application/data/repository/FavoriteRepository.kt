package com.example.github_user_application.data.repository

import androidx.lifecycle.LiveData
import com.example.github_user_application.data.local.entity.FavoriteUserEntity
import com.example.github_user_application.data.local.room.FavoriteDao
import com.example.github_user_application.data.remote.retrofit.ApiService
import com.example.github_user_application.utils.AppExecutors


class FavoriteRepository private constructor(
    private val apiService: ApiService,
    private val favoriteDao:FavoriteDao,
    private val appExecutors: AppExecutors
){

    fun getFavorites():LiveData<List<FavoriteUserEntity>>{
        return favoriteDao.getFavorite()
    }
    fun insertFavorite(favorite:FavoriteUserEntity){
        appExecutors.diskIO.execute{
            favoriteDao.insertFav(favorite)
        }
    }
    fun deleteFavorite(username:String){
        appExecutors.diskIO.execute{
            favoriteDao.deleteFav(username)
        }
    }
    fun isFavorited(username: String): LiveData<Boolean> {
        return favoriteDao.isFavorited(username)
    }

    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null
        fun getInstance(
            apiService: ApiService,
            favoriteDao: FavoriteDao,
            appExecutors: AppExecutors
        ): FavoriteRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteRepository(apiService, favoriteDao, appExecutors)
            }.also { instance = it }
    }
}