package com.task.movieapp.details.presentation

import com.task.movieapp.movielist.domain.model.Movie

data class DetailsState(
    val isLoading: Boolean = false,
    val movie: Movie? = null,
)
