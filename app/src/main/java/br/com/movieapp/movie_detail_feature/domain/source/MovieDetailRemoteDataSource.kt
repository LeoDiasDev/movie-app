package br.com.movieapp.movie_detail_feature.domain.source

import br.com.movieapp.core.data.remote.response.MovieResponse
import br.com.movieapp.core.domain.model.MovieDetails
import br.com.movieapp.core.domain.model.MoviePaging
import br.com.movieapp.core.paging.MovieSimiliarPagingSource

interface MovieDetailRemoteDataSource {

    suspend fun getMovieDetails(movieId: Int): MovieDetails

    suspend fun getMoviesSimiliar(page: Int, movieId: Int): MoviePaging

    fun getSimilarMoviesPagingSource(movieId: Int): MovieSimiliarPagingSource

}