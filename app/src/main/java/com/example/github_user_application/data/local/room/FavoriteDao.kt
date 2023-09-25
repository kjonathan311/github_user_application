package com.example.github_user_application.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.github_user_application.data.local.entity.FavoriteUserEntity

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite")
    fun getFavorite(): LiveData<List<FavoriteUserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFav(Fav:FavoriteUserEntity)

    @Query("DELETE FROM favorite where username=:username")
    fun deleteFav(username:String)

    @Query("SELECT EXISTS(SELECT * FROM favorite WHERE username = :username)")
    fun isFavorited(username: String): LiveData<Boolean>
}