package com.task.movieapp.movielist.data.mappers

import com.task.movieapp.movielist.data.local.MovieEntity
import com.task.movieapp.movielist.data.remote.respond.MovieDto
import com.task.movieapp.movielist.domain.model.Movie



fun MovieDto.toMovieEntity(
    category: String
): MovieEntity {
    return MovieEntity(
        adult = adult ?: false,
        backdrop_path = backdrop_path ?: "",
        original_language = original_language ?: "",
        overview = overview ?: "",
        poster_path = poster_path ?: "",
        release_date = release_date ?: "",
        title = title ?: "",
        vote_average = vote_average ?: 0.0,
        popularity = popularity ?: 0.0,
        vote_count = vote_count ?: 0,
        id = id ?: -1,
        original_title = original_title ?: "",
        video = video ?: false,

        category = category,

        genre_ids = try {
            genre_ids?.joinToString(",") ?: "-1,-2"
        } catch (e: Exception) {
            "-1,-2"
        }
    )
}

fun MovieEntity.toMovie(
    category: String
): Movie {
    return Movie(
        backdropPath = backdrop_path,
        originalLanguage = original_language,
        overview = overview,
        posterPath = poster_path,
        releaseDate = release_date,
        title = title,
        voteAverage = vote_average,
        popularity = popularity,
        voteCount = vote_count,
        video = video,
        id = id,
        adult = adult,
        originalTitle = original_title,

        category = category,

        genreIds = try {
            genre_ids.split(",").map { it.toInt() }
        } catch (e: Exception) {
            listOf(-1, -2)
        }
    )
}