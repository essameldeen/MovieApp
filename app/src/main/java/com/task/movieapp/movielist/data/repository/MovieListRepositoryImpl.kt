package com.task.movieapp.movielist.data.repository

import com.task.movieapp.movielist.data.local.MovieDao
import com.task.movieapp.movielist.data.local.MovieDatabase
import com.task.movieapp.movielist.data.mappers.toMovie
import com.task.movieapp.movielist.data.mappers.toMovieEntity
import com.task.movieapp.movielist.data.remote.MovieApi
import com.task.movieapp.movielist.domain.model.Movie
import com.task.movieapp.movielist.domain.repository.MovieRepository
import com.task.movieapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class MovieListRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val dao: MovieDao
) : MovieRepository {
    override suspend fun getMovieList(
        fetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading(true))
        val localMovieList = dao.getMovieByCategory(category)
        val shouldLoadLocalMovie = localMovieList.isNotEmpty() && !fetchFromRemote
        if (shouldLoadLocalMovie) {
            emit(Resource.Success(
                data = localMovieList.map { it.toMovie(category) }
            ))
            emit(Resource.Loading(false))
            return@flow
        }
        val movieListFromApi = try {
            movieApi.getMoviesList(category, page)
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Resource.Error("Error Loading Movies from Server."))
            return@flow
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Resource.Error("Error Loading Movies from Server."))
            return@flow
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error("Error Loading Movies from Server."))
            return@flow
        }
        val movieEntities = movieListFromApi.results.let {
            it.map { movieDto ->
                movieDto.toMovieEntity(category)
            }
        }

        dao.upsertMoviesList(movieEntities)

        emit(Resource.Success(
            data = movieEntities.map { it.toMovie(category) }
        ))
        emit(Resource.Loading(false))

    }

    override suspend fun getMovieById(id: Int): Flow<Resource<Movie>> = flow {
        emit(Resource.Loading(true))
        val movieEntity = dao.getMovieById(id)
        if (movieEntity != null) {
            emit(Resource.Success(movieEntity.toMovie(movieEntity.category)))
            emit(Resource.Loading(false))
            return@flow
        }

        emit(Resource.Error("Movie Not Found"))
        emit(Resource.Loading(false))
    }
}