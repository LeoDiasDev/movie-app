package br.com.movieapp.movie_search_feature.domain.source

import br.com.movieapp.core.data.remote.response.SearchResponse
import br.com.movieapp.core.domain.model.MovieSearchPaging
import br.com.movieapp.core.paging.MovieSearchPagingSource

interface MovieSearchRemoteDataSource {

    fun getSearchMoviePagingSource(query:String) : MovieSearchPagingSource

    suspend fun getSearchMovies(page: Int, query: String): MovieSearchPaging

}