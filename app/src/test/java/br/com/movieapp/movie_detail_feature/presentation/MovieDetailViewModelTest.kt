package br.com.movieapp.movie_detail_feature.presentation

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
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
import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieDetailsFactory
import br.com.movieapp.core.domain.model.MovieFactory
import br.com.movieapp.core.util.ResultData
import br.com.movieapp.movie_detail_feature.data.mapper.toMovie
import br.com.movieapp.movie_detail_feature.domain.usecase.GetMovieDetailsUseCase
import br.com.movieapp.movie_favorite_feature.domain.usecase.AddMovieFavoriteUseCase
import br.com.movieapp.movie_favorite_feature.domain.usecase.DeleteMovieFavoriteUseCase
import br.com.movieapp.movie_favorite_feature.domain.usecase.IsMovieFavoriteUseCase
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieDetailViewModelTest{

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    @Mock
    lateinit var addMovieFavoriteUseCase: AddMovieFavoriteUseCase

    @Mock
    lateinit var deleteMovieFavoriteUseCase: DeleteMovieFavoriteUseCase

    @Mock
    lateinit var isMovieFavoriteUseCase: IsMovieFavoriteUseCase

    @Mock
    lateinit var savedStateHandle: SavedStateHandle

    private val movieDetailsFactory =
        MovieDetailsFactory().create(poster = MovieDetailsFactory.Poster.Avengers)

    private val pagingData = PagingData.from(
        listOf(
            MovieFactory().create(poster = MovieFactory.Poster.Avengers),
            MovieFactory().create(poster = MovieFactory.Poster.JonhWick)
        )
    )

    private val movie = MovieFactory().create(poster = MovieFactory.Poster.Avengers)

    private val viewModel by lazy{
        MovieDetailViewModel(
            getMovieDetailsUseCase = getMovieDetailsUseCase,
            addMovieFavoriteUseCase = addMovieFavoriteUseCase,
            deleteMovieFavoriteUseCase = deleteMovieFavoriteUseCase,
            isMovieFavoriteUseCase = isMovieFavoriteUseCase,
            savedStateHandle.apply {
                whenever(savedStateHandle.get<Int>("movieId")).thenReturn(movie.id)
            }
        )
    }

    @Test
    fun `must notify uiState with sucess when get movies similar and movie details returns sucess`() = runTest {

        //Given
        whenever(getMovieDetailsUseCase.invoke(any()))
            .thenReturn(ResultData.Sucess(flowOf(pagingData) to movieDetailsFactory))

        whenever(isMovieFavoriteUseCase.invoke(any()))
            .thenReturn(flowOf(ResultData.Sucess(true)))

        val argumentCaptor = argumentCaptor<GetMovieDetailsUseCase.Params>()
        val checkedArgumentCaptor = argumentCaptor<IsMovieFavoriteUseCase.Params>()

        //When
        viewModel.uiState.isLoading

        //Then
        verify(getMovieDetailsUseCase).invoke(argumentCaptor.capture())
        assertThat(movieDetailsFactory.id).isEqualTo(argumentCaptor.firstValue.movieId)

        verify(isMovieFavoriteUseCase).invoke(checkedArgumentCaptor.capture())
        assertThat(movie.id).isEqualTo(checkedArgumentCaptor.firstValue.movieId)

        val movieDetails = viewModel.uiState.movieDetails
        val result = viewModel.uiState.results
        
        assertThat(movieDetails).isNotNull()
        assertThat(result).isNotNull()
    }

    @Test
    fun `must notify uiState whit Failure when getMovies details and returns exception`() = runTest {

        //Given
        val exception = Exception("Um erro ocorreu!")
        whenever(getMovieDetailsUseCase.invoke(any()))
            .thenReturn(ResultData.Failure(exception))

        whenever(isMovieFavoriteUseCase.invoke(any()))
            .thenReturn(flowOf(ResultData.Failure(exception)))


        //When
        viewModel.uiState.isLoading

        //Then
        val error = viewModel.uiState.error
        assertThat(exception.message).isEqualTo(error)

    }

    @Test
    fun `must call delete favorite and notify of uiState with failed favorite icon when current icon is Checked`() = runTest {

        //Given
        whenever(deleteMovieFavoriteUseCase.invoke(any()))
            .thenReturn(flowOf(ResultData.Sucess(Unit)))

        whenever(isMovieFavoriteUseCase.invoke(any()))
            .thenReturn(flowOf(ResultData.Sucess(true)))

        val deleteArgumentCaptor = argumentCaptor<DeleteMovieFavoriteUseCase.Params>()
        val checkedArgumentCaptor = argumentCaptor<IsMovieFavoriteUseCase.Params>()

        //When
        viewModel.onAddFavorite(movie = movie)

        //Then
        verify(deleteMovieFavoriteUseCase).invoke(deleteArgumentCaptor.capture())
        assertThat(movie).isEqualTo(deleteArgumentCaptor.firstValue.movie)

        verify(isMovieFavoriteUseCase).invoke(checkedArgumentCaptor.capture())
        assertThat(movie.id).isEqualTo(checkedArgumentCaptor.firstValue.movieId)

        val iconColor = viewModel.uiState.iconColor
        assertThat(Color.White).isEqualTo(iconColor)
    }

    @Test
    fun `must notify uiState with filled favorite icon when current icon is unchecked`() = runTest {

        //Given
        whenever(addMovieFavoriteUseCase.invoke(any()))
            .thenReturn(flowOf(ResultData.Sucess(Unit)))

        whenever(isMovieFavoriteUseCase.invoke(any()))
            .thenReturn(flowOf(ResultData.Sucess(false)))

        val addArgumentCaptor = argumentCaptor<AddMovieFavoriteUseCase.Params>()
        val checkedArgumentCaptor = argumentCaptor<IsMovieFavoriteUseCase.Params>()

        //When
        viewModel.onAddFavorite(movie = movie)

        //Then
        verify(addMovieFavoriteUseCase).invoke(addArgumentCaptor.capture())
        assertThat(movie).isEqualTo(addArgumentCaptor.firstValue.movie)

        verify(isMovieFavoriteUseCase).invoke(checkedArgumentCaptor.capture())
        assertThat(movie.id).isEqualTo(checkedArgumentCaptor.firstValue.movieId)

        val iconColor = viewModel.uiState.iconColor
        assertThat(Color.Red).isEqualTo(iconColor)
    }

    @Test
    fun `must notify uiState with bookmark icon filled in if bookmark check returns true`() = runTest {

        //Given
        whenever(isMovieFavoriteUseCase.invoke(any()))
            .thenReturn(flowOf(ResultData.Sucess(true)))

        whenever(getMovieDetailsUseCase.invoke(any()))
            .thenReturn(ResultData.Sucess(flowOf(pagingData) to movieDetailsFactory))

        val checkedArgumentCaptor = argumentCaptor<IsMovieFavoriteUseCase.Params>()

        //When
        viewModel.uiState.isLoading

        verify(isMovieFavoriteUseCase).invoke(checkedArgumentCaptor.capture())
        assertThat(movie.id).isEqualTo(checkedArgumentCaptor.firstValue.movieId)

        val iconColor = viewModel.uiState.iconColor
        assertThat(Color.Red).isEqualTo(iconColor)
    }

    @Test
    fun `must notify uiState with bookmark icon filled in if bookmark check returns false`() = runTest {

        //Given
        whenever(isMovieFavoriteUseCase.invoke(any()))
            .thenReturn(flowOf(ResultData.Sucess(false)))

        whenever(getMovieDetailsUseCase.invoke(any()))
            .thenReturn(ResultData.Sucess(flowOf(pagingData) to movieDetailsFactory))

        val checkedArgumentCaptor = argumentCaptor<IsMovieFavoriteUseCase.Params>()

        //When
        viewModel.uiState.isLoading

        verify(isMovieFavoriteUseCase).invoke(checkedArgumentCaptor.capture())
        assertThat(movie.id).isEqualTo(checkedArgumentCaptor.firstValue.movieId)

        val iconColor = viewModel.uiState.iconColor
        assertThat(Color.White).isEqualTo(iconColor)
    }
}