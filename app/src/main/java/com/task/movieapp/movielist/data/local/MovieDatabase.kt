package com.task.movieapp.movielist.data.local

import androidx.room.Database
import androidx.room.Entity
import androidx.room.RoomDatabase


@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract val movieDao: MovieDao
}