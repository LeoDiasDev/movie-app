package br.com.movieapp.movie_search_feature.data.source

import br.com.movieapp.core.data.remote.MovieService
import br.com.movieapp.core.data.remote.response.SearchResponse
import br.com.movieapp.core.domain.model.MovieSearchPaging
import br.com.movieapp.core.paging.MovieSearchPagingSource
import br.com.movieapp.movie_search_feature.data.mapper.toMovieSearch
import br.com.movieapp.movie_search_feature.domain.source.MovieSearchRemoteDataSource
import javax.inject.Inject

class MovieSearchRemoteDataSourceImpl @Inject constructor(
    private val service: MovieService
): MovieSearchRemoteDataSource {

    override fun getSearchMoviePagingSource(query: String): MovieSearchPagingSource {
        return MovieSearchPagingSource(query = query, remoteDataSource = this)
    }

    override suspend fun getSearchMovies(page: Int, query: String): MovieSearchPaging {

        val response = service.searchMovie(page = page, query = query)

        return MovieSearchPaging(
            paging = response.page,
            totalPages = response.totalPages,
            totalResults = response.totalResults,
            movies = response.results.map { it.toMovieSearch() }
        )
    }

}