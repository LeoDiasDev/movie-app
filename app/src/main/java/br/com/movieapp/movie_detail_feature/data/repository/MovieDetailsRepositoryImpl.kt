package br.com.movieapp.movie_detail_feature.data.repository

import androidx.paging.PagingSource
import br.com.movieapp.core.domain.model.Movie
import br.com.movieapp.core.domain.model.MovieDetails
import br.com.movieapp.core.paging.MovieSimiliarPagingSource
import br.com.movieapp.movie_detail_feature.domain.repository.MovieDetailsRepository
import br.com.movieapp.movie_detail_feature.domain.source.MovieDetailRemoteDataSource
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieDetailRemoteDataSource
) : MovieDetailsRepository {

    override suspend fun getMovieDetails(movieId: Int): MovieDetails {
        return remoteDataSource.getMovieDetails(movieId)
    }

    override fun getMoviesSimiliar(
        movieId: Int
    ): PagingSource<Int, Movie> {
        return MovieSimiliarPagingSource(movieId = movieId, remoteDataSource = remoteDataSource)
    }
}