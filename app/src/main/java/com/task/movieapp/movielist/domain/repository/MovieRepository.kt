package com.task.movieapp.movielist.domain.repository

import com.task.movieapp.movielist.domain.model.Movie
import com.task.movieapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMovieList(
        fetchFromRemote: Boolean,
        category: String,
        page: Int,

        ): Flow<Resource<List<Movie>>>

    suspend fun getMovieById(
        id: Int
    ): Flow<Resource<Movie>>
}