package com.example.sophossolutions.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sophossolutions.models.User
import com.example.sophossolutions.models.UserDao

@Database(entities = [User::class], version = 1)
abstract class DbDataSource: RoomDatabase() {

    abstract fun userDao(): UserDao
}