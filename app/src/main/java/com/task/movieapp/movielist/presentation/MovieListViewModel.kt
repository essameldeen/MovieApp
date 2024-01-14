package com.task.movieapp.movielist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.movieapp.movielist.domain.repository.MovieRepository
import com.task.movieapp.utils.Category
import com.task.movieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private var _movieListState = MutableStateFlow(MovieListState())
    val movieListState = _movieListState.asStateFlow()

    init {
        getPopularMovies(false)
        getUpComingMovieList(false)
    }

    fun onEvent(event: MovieListUiEvent) {
        when (event) {
            is MovieListUiEvent.Paginate -> {
                if (event.category == Category.POPULAR) {
                    getPopularMovies(true)

                } else if (event.category == Category.UPCOMING) {
                    getUpComingMovieList(true)
                }
            }

            is MovieListUiEvent.Navigate -> {
                _movieListState.update {
                    it.copy(
                        isCurrentPopularScreen = !_movieListState.value.isCurrentPopularScreen
                    )
                }
            }
        }
    }


    private fun getUpComingMovieList(fetchFromRemote: Boolean) {
        viewModelScope.launch {
            _movieListState.update {
                it.copy(
                    isLoading = true
                )
            }

            repository.getMovieList(
                fetchFromRemote,
                Category.UPCOMING,
                _movieListState.value.movieListUpComingPage
            ).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _movieListState.update {
                            it.copy(
                                isLoading = result.isLoading
                            )
                        }
                    }

                    is Resource.Error -> {
                        _movieListState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Success -> {

                        result.data.let {
                            _movieListState.update {
                                it.copy(
                                    upComingMovieList = movieListState.value.upComingMovieList + it.upComingMovieList.shuffled(),
                                    movieListUpComingPage = movieListState.value.movieListUpComingPage + 1
                                )
                            }

                        }

                        _movieListState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                    }
                }
            }



            _movieListState.update {
                it.copy(
                    isLoading = false
                )
            }


        }
    }

    private fun getPopularMovies(fetchFromRemote: Boolean) {
        viewModelScope.launch {
            _movieListState.update {
                it.copy(
                    isLoading = true
                )
            }

            repository.getMovieList(
                fetchFromRemote,
                Category.POPULAR,
                _movieListState.value.movieListPopularPage
            ).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _movieListState.update {
                            it.copy(
                                isLoading = result.isLoading
                            )
                        }
                    }

                    is Resource.Error -> {
                        _movieListState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Success -> {

                        result.data.let {
                            _movieListState.update {
                                it.copy(
                                    popularMovieList = movieListState.value.popularMovieList + it.popularMovieList.shuffled(),
                                    movieListPopularPage = movieListState.value.movieListPopularPage + 1
                                )
                            }

                        }

                        _movieListState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                    }
                }
            }
            _movieListState.update {
                it.copy(
                    isLoading = false
                )
            }


        }
    }
}