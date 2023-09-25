package com.example.github_user_application.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.github_user_application.data.repository.FavoriteRepository
import com.example.github_user_application.data.local.entity.FavoriteUserEntity

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository):ViewModel() {
    fun getFavorite()=favoriteRepository.getFavorites()

    fun isFavorited(username: String): LiveData<Boolean> {
        return favoriteRepository.isFavorited(username)
    }

    fun saveFav(favorite:FavoriteUserEntity){
        favoriteRepository.insertFavorite(favorite)
    }
    fun deleteFav(username: String){
        favoriteRepository.deleteFavorite(username)
    }
}