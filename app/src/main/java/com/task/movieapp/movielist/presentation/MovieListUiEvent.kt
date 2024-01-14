package com.task.movieapp.movielist.presentation

sealed class MovieListUiEvent {
    data class Paginate(val category: String) : MovieListUiEvent()
    data object Navigate : MovieListUiEvent()
}