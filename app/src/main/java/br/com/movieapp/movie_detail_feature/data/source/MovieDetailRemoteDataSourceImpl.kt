package br.com.movieapp.movie_detail_feature.data.source

import br.com.movieapp.core.data.remote.MovieService
import br.com.movieapp.core.data.remote.response.MovieResponse
import br.com.movieapp.core.domain.model.MovieDetails
import br.com.movieapp.core.domain.model.MoviePaging
import br.com.movieapp.core.paging.MovieSimiliarPagingSource
import br.com.movieapp.core.util.toBackDropUrl
import br.com.movieapp.movie_detail_feature.domain.source.MovieDetailRemoteDataSource
import br.com.movieapp.movie_popular_feature.data.mapper.toMovie
import javax.inject.Inject

class MovieDetailRemoteDataSourceImpl @Inject constructor(
    private val service: MovieService
) : MovieDetailRemoteDataSource {

    override suspend fun getMovieDetails(movieId: Int): MovieDetails {
        val response = service.getMovie(movieId)
        val genres = response.genres.map { it.name }
        return MovieDetails(
            id = response.id,
            title = response.title,
            overview = response.overview,
            genres = genres,
            releaseDate = response.releaseDate,
            backDropPathUrl = response.backdropPath.toBackDropUrl(),
            voteAvarage = response.voteAverage,
            duration = response.runtime,
            voteCount = response.voteCount
        )
    }

    override suspend fun getMoviesSimiliar(page: Int, movieId: Int): MoviePaging {

        val response = service.getMoviesSimilar(page = page, movieId = movieId)

        return MoviePaging(
            paging = response.page,
            totalResults = response.totalResults,
            totalPages = response.totalPages,
            movies = response.results.map { it.toMovie() }
       )
    }

    override fun getSimilarMoviesPagingSource(movieId: Int): MovieSimiliarPagingSource {
        return MovieSimiliarPagingSource(this, movieId = movieId)
    }

}