package br.com.movieapp.core.domain.model

class MoviePagingFactory {

    fun create() = MoviePaging(
        paging = 1,
        totalPages = 1,
        totalResults = 2,
        movies = listOf(
            Movie(
                id = 1,
                title = "Avengers",
                voteAvarage = 7.1,
                imageUrl = "Url"
            ),
            Movie(
                id = 2,
                title = "JonhWick",
                voteAvarage = 7.9,
                imageUrl = "Url"
            )
        )
    )
}