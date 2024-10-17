package br.com.movieapp.core.domain.model

data class MovieDetails(
    val id: Int,
    val title: String,
    val genres: List<String>,
    val overview: String?,
    val backDropPathUrl: String?,
    val releaseDate: String?,
    val voteAvarage: Double,
    val duration: Int = 0,
    val voteCount: Int = 0
)
