package com.task.movieapp.movielist.data.remote

import com.task.movieapp.utils.Constant
import com.task.movieapp.movielist.data.remote.respond.MovieListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/{category}")
    suspend fun getMoviesList(
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = Constant.APIKEY
    ):MovieListDto
}