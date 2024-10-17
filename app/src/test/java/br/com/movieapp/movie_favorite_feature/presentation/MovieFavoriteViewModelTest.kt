package br.com.movieapp.movie_favorite_feature.presentation

import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieFactory
import br.com.movieapp.movie_favorite_feature.domain.usecase.GetMoviesMovieFavoriteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
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
class MovieFavoriteViewModelTest{

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var getMoviesMovieFavoriteUseCase: GetMoviesMovieFavoriteUseCase

    private val viewModel by lazy {
        MovieFavoriteViewModel(getMoviesMovieFavoriteUseCase)
    }

    private val moviesFavorite = listOf(
            MovieFactory().create(poster = MovieFactory.Poster.Avengers),
            MovieFactory().create(poster = MovieFactory.Poster.JonhWick)
        )

    @Test
    fun `must validate the data object values when calling list of favorites`() = runTest {

        //Given
        whenever(getMoviesMovieFavoriteUseCase.invoke()).thenReturn(
            flowOf(moviesFavorite)
        )

        //When
        val result = viewModel.uiState.movies.first()

        //Then
        assertThat(result).isNotEmpty()
        assertThat(result).contains(moviesFavorite[0])

    }

}