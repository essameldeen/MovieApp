package com.task.movieapp.movielist.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MovieDao {

    @Upsert
    suspend fun upsertMoviesList(
        movieList: List<MovieEntity>
    )

    @Query("SELECT * from MovieEntity WHERE id = :id")
    suspend fun getMovieById(id: Int): MovieEntity

    @Query("SELECT * from MovieEntity WHERE category = :category")
    suspend fun getMovieByCategory(category: String): List<MovieEntity>

}