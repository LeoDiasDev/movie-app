package br.com.movieapp.core.domain.model

class MovieFactory {

    fun create(
        poster: Poster
    ) = when(poster){
        Poster.Avengers -> {
            Movie(
                id = 1,
                title = "Avengers",
                voteAvarage = 7.1,
                imageUrl = "Url"
            )
        }
        Poster.JonhWick -> {
            Movie(
                id = 2,
                title = "JonhWick",
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