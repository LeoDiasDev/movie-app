package br.com.movieapp.movie_search_feature

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.movieapp.R
import br.com.movieapp.core.presentation.components.common.MovieAppBar
import br.com.movieapp.movie_search_feature.presentation.MovieSearchEvent
import br.com.movieapp.movie_search_feature.presentation.components.SearchContent
import br.com.movieapp.movie_search_feature.presentation.state.MovieSearchState

@Composable
fun MovieSearchScreen(
    uiState: MovieSearchState,
    onEvent: (MovieSearchEvent) -> Unit,
    onFetch: (String) -> Unit,
    navigateToDetailMovie: (Int) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val pagingMovies = uiState.movies.collectAsLazyPagingItems()

    Scaffold (
        topBar = {
            MovieAppBar(
                title = R.string.search_movies
            )
        },
        content = {paddingValues ->
            SearchContent(
                paddingValues = paddingValues,
                pagingMovies = pagingMovies,
                query = uiState.query,
                onSearch = {
                    onFetch(it)
                    focusManager.clearFocus()
                },
                onEvent = {
                   onEvent(it)
                },
                onDetail = { movieId ->
                    navigateToDetailMovie(movieId)
                }
            )
        }
    )
}

@Preview
@Composable
fun MovieSearchScreenPreview() {
    MovieSearchScreen(
        uiState = MovieSearchState(),
        onEvent = {},
        onFetch = {},
        navigateToDetailMovie = {}
    )
}