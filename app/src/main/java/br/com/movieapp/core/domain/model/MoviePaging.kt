package br.com.movieapp.core.domain.model

data class MoviePaging(
    val paging: Int,
    val totalPages: Int,
    val totalResults: Int,
    val movies: List<Movie>
)
