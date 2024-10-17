package br.com.movieapp.movie_search_feature.presentation

import androidx.paging.PagingData
import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieSearchFactory
import br.com.movieapp.movie_search_feature.domain.usecase.GetMovieSearchUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import com.google.common.truth.Truth.assertThat
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mock

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieSearchViewModelTest{

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var getSearchMovieUseCase: GetMovieSearchUseCase

    private val viewModel by lazy{
        MovieSearchViewModel(getSearchMovieUseCase)
    }

    private val fakePagingDataSearchMovies = PagingData.from(
        listOf(
            MovieSearchFactory().create(poster = MovieSearchFactory.Poster.Avengers),
            MovieSearchFactory().create(poster = MovieSearchFactory.Poster.JonhWick)
        )
    )

    @Test
    fun `must validate paging data object values when calling movie searching paging data`() = runTest {

        //Given
        whenever(getSearchMovieUseCase.invoke(any())).thenReturn(
            flowOf(fakePagingDataSearchMovies)
        )

        //When
        viewModel.fetch("")
        val result = viewModel.uiState.movies.first()

        //Then
        assertThat(result).isNotNull()
    }

    @Test(expected = RuntimeException::class)
    fun `must throw an exception when the calling to the use case returns an exception`() = runTest {
        //Given
        whenever(getSearchMovieUseCase.invoke(any()))
            .thenThrow(RuntimeException())

        //When
        viewModel.fetch("")
    }
}