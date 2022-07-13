package com.example.tayapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserInfo(
    @PrimaryKey
    val id:Int? = 0
)