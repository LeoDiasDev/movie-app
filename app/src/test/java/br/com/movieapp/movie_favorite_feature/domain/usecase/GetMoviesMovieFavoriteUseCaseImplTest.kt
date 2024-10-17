package br.com.movieapp.movie_favorite_feature.domain.usecase

import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieFactory
import br.com.movieapp.core.util.ResultData
import br.com.movieapp.movie_favorite_feature.domain.repository.MovieFavoriteRepository
import br.com.movieapp.movie_popular_feature.domain.repository.MoviePopularRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetMoviesMovieFavoriteUseCaseImplTest{

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var movieFavoriteRepository: MovieFavoriteRepository

    private val movies = listOf(
        MovieFactory().create(poster = MovieFactory.Poster.Avengers),
        MovieFactory().create(poster = MovieFactory.Poster.JonhWick)
    )

    private val getMoviesMovieFavoriteUseCase by lazy {
        GetMoviesMovieFavoriteUseCaseImpl(
            movieFavoriteRepository = movieFavoriteRepository
        )
    }

    @Test
    fun `should return Sucess from ResultStatus when the resposity returns a list of movies`() = runTest {
        //GIven
        whenever(movieFavoriteRepository.getMovies()).thenReturn(
            flowOf(movies)
        )

        //When
        val result = getMoviesMovieFavoriteUseCase.invoke().first()

        //Then
        assertThat(result).isNotEmpty()
        assertThat(result).contains(movies[1])
    }

    @Test
    fun `should emite an empty stream when exception is throw when calling the invoke method`() = runTest {

        //Given
        val exception = RuntimeException()

        whenever(movieFavoriteRepository.getMovies())
            .thenThrow(exception)

        //When
        val result = getMoviesMovieFavoriteUseCase.invoke().toList()

        //Then
        verify(movieFavoriteRepository).getMovies()
        assertThat(result).isEmpty()
    }
}