package br.com.movieapp.movie_favorite_feature.domain.usecase

import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieFactory
import br.com.movieapp.core.util.ResultData
import br.com.movieapp.movie_favorite_feature.domain.repository.MovieFavoriteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DeleteMovieFavoriteUseCaseImplTest{

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var movieFavoriteRepository: MovieFavoriteRepository

    private val movie = MovieFactory().create(poster = MovieFactory.Poster.Avengers)

    private val deleteMovieFavoriteUseCase by lazy{
        DeleteMovieFavoriteUseCaseImpl(
            movieFavoriteRepository = movieFavoriteRepository
        )
    }

    @Test
    fun `should return Sucess from ResultStatus when the resposity returns sucess equal to unit`() = runTest {

        //Given
        whenever(movieFavoriteRepository.delete(movie)).thenReturn(Unit)

        //When
        val result = deleteMovieFavoriteUseCase.invoke(
            params = DeleteMovieFavoriteUseCase.Params(
                movie = movie
            )
        ).first()

        //Then
        assertThat(result).isEqualTo(ResultData.Sucess(Unit))
    }

    @Test
    fun `should return Failure from ResultStatus when the repository return throws an exception`() = runTest {

        //Given
        val exception = RuntimeException()

        whenever(movieFavoriteRepository.delete(movie))
            .thenThrow(exception)

        //When
        val result = deleteMovieFavoriteUseCase.invoke(
            params = DeleteMovieFavoriteUseCase.Params(movie = movie)
        ).first()

        //Then
        assertThat(result).isEqualTo(ResultData.Failure(exception))
    }

}