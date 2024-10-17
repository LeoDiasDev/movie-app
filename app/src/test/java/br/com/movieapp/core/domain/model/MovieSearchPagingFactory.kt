package br.com.movieapp.core.domain.model

class MovieSearchPagingFactory {

    fun create() = MovieSearchPaging(
        paging = 1,
        totalPages = 1,
        totalResults = 2,
        movies = listOf(
            MovieSearch(
                id = 1,
                voteAvarage = 7.1,
                imageUrl = "Url"
            ),
            MovieSearch(
                id = 2,
                voteAvarage = 7.9,
                imageUrl = "Url"
            )
        )
    )
}