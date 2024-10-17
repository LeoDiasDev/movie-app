package br.com.movieapp.movie_favorite_feature

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.com.movieapp.R
import br.com.movieapp.core.domain.model.Movie
import br.com.movieapp.core.presentation.components.common.MovieAppBar
import br.com.movieapp.movie_favorite_feature.presentation.components.MovieFavoriteContent
import br.com.movieapp.movie_favorite_feature.presentation.state.MovieFavoriteState
import br.com.movieapp.ui.theme.black

@Composable
fun MovieFavoriteScreen(
    movies: List<Movie>,
    navigateToDetailMovie: (Int) -> Unit
) {
    Scaffold (
        topBar = {
            MovieAppBar(
                title = R.string.favorite_movies
            )
        },
        backgroundColor = black,
        content = { paddingValues ->
            MovieFavoriteContent(
                paddingValues = paddingValues,
                movies = movies,
                onClick = { movieId ->
                    navigateToDetailMovie(movieId)
                }
            )
        }
    )
}

@Preview
@Composable
fun MovieFavoriteScreenPreview() {
    MovieFavoriteScreen(
        movies = emptyList()
    ) {}
}