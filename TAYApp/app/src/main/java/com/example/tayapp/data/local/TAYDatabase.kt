package com.example.tayapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tayapp.data.local.entities.UserInfo

@Database(
    entities = [UserInfo::class],
    version = 1,
    exportSchema = false
)
abstract class TayDatabase:RoomDatabase() {

    abstract val dao: TayDao
}