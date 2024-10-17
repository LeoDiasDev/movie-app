package br.com.movieapp.movie_search_feature.domain.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import br.com.movieapp.core.domain.model.MovieSearch
import kotlinx.coroutines.flow.Flow

interface MovieSearchRepository {

    fun getSearchMovies(query: String) : PagingSource<Int, MovieSearch>

}