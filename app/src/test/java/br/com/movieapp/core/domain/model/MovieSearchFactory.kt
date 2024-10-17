package br.com.movieapp.core.domain.model

class MovieSearchFactory {
    fun create(
        poster: Poster
    ) = when(poster){
        Poster.Avengers -> {
            MovieSearch(
                id = 1,
                voteAvarage = 7.1,
                imageUrl = "Url"
            )
        }
        Poster.JonhWick -> {
            MovieSearch(
                id = 2,
                voteAvarage = 7.9,
                imageUrl = "Url"
            )
        }
    }

    sealed class Poster{
        object Avengers: Poster()
        object JonhWick: Poster()
    }
}