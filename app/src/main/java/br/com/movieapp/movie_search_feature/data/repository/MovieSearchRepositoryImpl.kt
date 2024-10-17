package br.com.movieapp.movie_search_feature.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import br.com.movieapp.core.domain.model.MovieSearch
import br.com.movieapp.core.paging.MovieSearchPagingSource
import br.com.movieapp.movie_search_feature.domain.repository.MovieSearchRepository
import br.com.movieapp.movie_search_feature.domain.source.MovieSearchRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieSearchRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieSearchRemoteDataSource
) : MovieSearchRepository {

    override fun getSearchMovies(
        query: String
    ): PagingSource<Int, MovieSearch> {
        return MovieSearchPagingSource(query, remoteDataSource)
    }

}