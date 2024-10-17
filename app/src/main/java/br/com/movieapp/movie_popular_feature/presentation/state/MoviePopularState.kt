package br.com.movieapp.movie_popular_feature.presentation.state

import androidx.paging.PagingData
import br.com.movieapp.core.domain.model.Movie
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.Flow

data class MoviePopularState(
    val movies: Flow<PagingData<Movie>> = emptyFlow()


)
