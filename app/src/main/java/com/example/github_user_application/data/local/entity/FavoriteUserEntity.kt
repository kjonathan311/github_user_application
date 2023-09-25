package com.example.github_user_application.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
class FavoriteUserEntity (
    @field:ColumnInfo(name="username")
    @field:PrimaryKey
    var username:String,

    @field:ColumnInfo(name="avatarURL")
    var avatarURL:String,
)