package com.task.movieapp.movielist.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.task.movieapp.movielist.presentation.MovieListState
import com.task.movieapp.movielist.presentation.MovieListUiEvent
import com.task.movieapp.movielist.presentation.component.MovieItem
import com.task.movieapp.utils.Category
import com.task.movieapp.utils.Screen


@Composable
fun PopularMovieListScreen(
    navController: NavController,
    state: MovieListState,
    onEvent: (MovieListUiEvent) -> Unit
) {


    if (state.popularMovieList.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp)
        ) {
            items(state.popularMovieList.size) { index ->
                MovieItem(movie = state.popularMovieList[index]) { movie ->
                    navController.navigate(Screen.Details.rout + "/${movie.id}")
                }
                Spacer(modifier = Modifier.height(16.dp))

                if (index >= state.popularMovieList.size - 1 && !state.isLoading) {
                    onEvent(MovieListUiEvent.Paginate(Category.POPULAR))
                }
            }
        }
    }

}