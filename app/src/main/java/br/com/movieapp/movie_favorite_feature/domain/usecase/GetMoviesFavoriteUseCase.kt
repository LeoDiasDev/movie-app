package br.com.movieapp.movie_favorite_feature.domain.usecase

import br.com.movieapp.core.domain.model.Movie
import br.com.movieapp.movie_favorite_feature.domain.repository.MovieFavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

interface GetMoviesMovieFavoriteUseCase{
    suspend operator fun invoke(): Flow<List<Movie>>
}

class GetMoviesMovieFavoriteUseCaseImpl @Inject constructor(
    private val movieFavoriteRepository: MovieFavoriteRepository
): GetMoviesMovieFavoriteUseCase{

    override suspend fun invoke(): Flow<List<Movie>> {
        return try {
            movieFavoriteRepository.getMovies()
        }catch (e: Exception){
            emptyFlow()
        }
    }
}