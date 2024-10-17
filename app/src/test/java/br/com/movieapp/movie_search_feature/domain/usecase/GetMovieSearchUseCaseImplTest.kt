package br.com.movieapp.movie_search_feature.domain.usecase

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieSearchFactory
import br.com.movieapp.core.domain.model.PagingSourceMovieSearchFactory
import br.com.movieapp.movie_popular_feature.domain.usecase.GetPopularMoviesUseCase
import br.com.movieapp.movie_search_feature.domain.repository.MovieSearchRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.toList
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetMovieSearchUseCaseImplTest{

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var moviesSearchRepository: MovieSearchRepository

    private val movieSearchFactory = MovieSearchFactory()
        .create(poster = MovieSearchFactory.Poster.Avengers)

    private val getMovieSearchUseCase by lazy{
        GetMovieSearchUseCaseImpl(moviesSearchRepository)
    }

    private val pagingSourceFake = PagingSourceMovieSearchFactory().create(
        listOf(movieSearchFactory)
    )

    @Test
    fun `should validate flow paging data creation when invoke from use case is called`() = runTest {

        //Given
        whenever(moviesSearchRepository.getSearchMovies(query = ""))
            .thenReturn(pagingSourceFake)

        //When
        val result = getMovieSearchUseCase.invoke(
            params = GetMovieSearchUseCase.Params(
                query = "",
                pagingConfig = PagingConfig(
                    pageSize = 20,
                    initialLoadSize = 20
                )
            )
        ).first()

        //Then
        verify(moviesSearchRepository).getSearchMovies(query = "")
        assertThat(result).isNotNull()

    }

    @Test
    fun `should emit an empty stream when an exception is thrown when calling the invoke method`() = runTest {
        //Given
        val exception = RuntimeException()

        whenever(moviesSearchRepository.getSearchMovies(query = ""))
            .thenThrow(exception)

        //When
        val result = getMovieSearchUseCase.invoke(
            params = GetMovieSearchUseCase.Params(
                query = "",
                PagingConfig(
                    pageSize = 20,
                    initialLoadSize = 20
                )
            )
        ).toList()

        //Then
        verify(moviesSearchRepository).getSearchMovies(query = "")
        assertThat(result).isEmpty()
    }
}