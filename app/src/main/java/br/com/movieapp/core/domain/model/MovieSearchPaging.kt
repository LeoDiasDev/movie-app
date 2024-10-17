package br.com.movieapp.core.domain.model

data class MovieSearchPaging(
    val paging: Int,
    val totalPages: Int,
    val totalResults: Int,
    val movies: List<MovieSearch>
)
