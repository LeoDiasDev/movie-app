package br.com.movieapp.movie_search_feature.data.mapper

import br.com.movieapp.core.data.remote.model.SearchResult
import br.com.movieapp.core.domain.model.MovieSearch
import br.com.movieapp.core.util.toPostUrl

fun SearchResult.toMovieSearch(): MovieSearch{
    return MovieSearch(
        id = id,
        imageUrl = posterPath.toPostUrl(),
        voteAvarage = voteAverage
    )
}

fun List<SearchResult>.toMovieSearch() = map { searchResult ->
    MovieSearch(
        id = searchResult.id,
        imageUrl = searchResult.posterPath.toPostUrl(),
        voteAvarage = searchResult.voteAverage
    )
}