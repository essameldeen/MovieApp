package com.task.movieapp.movielist.presentation

import com.task.movieapp.movielist.domain.model.Movie

data class MovieListState(
    val isLoading: Boolean = false,
    val movieListPopularPage: Int = 1,
    val movieListUpComingPage: Int = 1,
    val popularMovieList: List<Movie> = listOf(),
    val upComingMovieList: List<Movie> = listOf(),
    val isCurrentPopularScreen: Boolean = true

)

