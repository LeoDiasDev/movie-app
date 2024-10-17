package br.com.movieapp.core.domain.model

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum

@Suppress("IMPLICIT_CAST_TO_ANY")
class MovieDetailsFactory {

    fun create(
        poster: Poster
    ) = when(poster){
        Poster.Avengers -> {
            MovieDetails(
                id = 1,
                title = "Avengers",
                voteAvarage = 7.1,
                genres = listOf("Aventura, Ação, Ficção Científica"),
                overview = LoremIpsum(100).values.first(),
                backDropPathUrl = "Url",
                releaseDate = "04/05/2012",
                duration = 143,
                voteCount = 7
            )
        }
        Poster.JonhWick -> {
            MovieDetails(
                id = 1,
                title = "Jonh Wick",
                voteAvarage = 7.1,
                genres = listOf("Aventura, Ação"),
                overview = LoremIpsum(100).values.first(),
                backDropPathUrl = "Url",
                releaseDate = "09/09/2012",
                duration = 143,
                voteCount = 7
            )
        }
    }

    sealed class Poster{
        object Avengers: Poster()
        object JonhWick: Poster()
    }
}