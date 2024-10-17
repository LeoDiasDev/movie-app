package br.com.movieapp.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import br.com.movieapp.core.data.local.MovieDataBase
import br.com.movieapp.core.data.local.dao.MovieDao
import br.com.movieapp.core.data.local.entity.MovieEntity
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@HiltAndroidTest
@SmallTest
class MovieDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var datebate: MovieDataBase

    private lateinit var movieDao: MovieDao

    @Before
    fun setup(){
        hiltRule.inject()
        movieDao = datebate.movieDao()
    }

    @After
    fun tearDown(){
        datebate.close()
    }

    @Test
    fun test_getMovies_should_return_list_of_movies() = runTest {

        //Given -> Nothing

        //When
        val movies = movieDao.getMovies().first()

        assertThat(movies.size).isEqualTo(0)
    }

    @Test
    fun test_getMovies_should_return_movies_orderedById() = runTest {
        //Given
        val moviesEntity = listOf(
            MovieEntity(movieId = 1, title = "Homem de Ferro 1", imageUrl = "Url1"),
            MovieEntity(movieId = 2, title = "Homem de Ferro 2", imageUrl = "Url2"),
            MovieEntity(movieId = 3, title = "Homem de Ferro 3", imageUrl = "Url3"),
            MovieEntity(movieId = 4, title = "Homem de Ferro 4", imageUrl = "Url4")
        )
        movieDao.insertMovies(moviesEntity)

        //When
        val movies = movieDao.getMovies().first()

        //Then
        assertThat(movies.size).isEqualTo(4)
        assertThat(movies[0].movieId).isEqualTo(1)
        assertThat(movies[1].movieId).isEqualTo(2)
        assertThat(movies[2].movieId).isEqualTo(3)
        assertThat(movies[3].movieId).isEqualTo(4)
    }

    @Test
    fun test_getMovie_should_return_correct_movieById() = runTest {
        //Given
        val movieEntity = MovieEntity(movieId = 1, title = "Homem de Ferro 1", imageUrl = "Url1")

        movieDao.insertMovie(movieEntity)

        val movies = movieDao.getMovies().first()
        val movieClick = movies[0]

        //When
        val movieId = movieDao.getMovie(movieClick.movieId)

        //Then
        assertThat(movieId?.title.equals(movieClick.title))
    }

    @Test
    fun test_insertMovies_should_insert_movies_sucessfuly() = runTest {
        //Given
        val moviesEntity = listOf(
            MovieEntity(movieId = 1, title = "Homem de Ferro 1", imageUrl = "Url1"),
            MovieEntity(movieId = 2, title = "Homem de Ferro 2", imageUrl = "Url2"),
            MovieEntity(movieId = 3, title = "Homem de Ferro 3", imageUrl = "Url3"),
            MovieEntity(movieId = 4, title = "Homem de Ferro 4", imageUrl = "Url4")
        )

        //When
        movieDao.insertMovies(moviesEntity)

        //Then
        val insertedMovies = movieDao.getMovies().first()
        assertThat(moviesEntity.size).isEqualTo(insertedMovies.size)
        assertThat(insertedMovies.containsAll(moviesEntity))
    }

    @Test
    fun test_insertMovie_should_insert_movie_sucessfuly() = runTest {
        //Given
        val movieEntity = MovieEntity(movieId = 1, title = "Homem de Ferro 1", imageUrl = "Url1")

        //When
        movieDao.insertMovie(movieEntity)

        //Then
        val movies = movieDao.getMovies().first()
        assertThat(movies[0].title).isEqualTo(movieEntity.title)
    }

    @Test
    fun test_isFavorite_should_returned_favorite_movie_when_movie_is_marked_as_favorite() = runTest {
        //Given
        val movieId = 5321
        val favoriteMovie = MovieEntity(movieId = movieId, title = "Avengers", imageUrl = "Url")
        movieDao.insertMovie(favoriteMovie)

        //When
        val result = movieDao.isFavorite(movieId = movieId)

        //Then
        assertThat(result).isEqualTo(favoriteMovie)
    }

    @Test
    fun test_updateMovie_should_update_a_movie_sucessfuly() = runTest {
        //Given
        val movieId = 5321

        //When
        val result = movieDao.isFavorite(movieId = movieId)

        //Then
        assertThat(result).isNull()
    }

    @Test
    fun test_isFavorite_should_return_null_when_is_not_marked_as_favorite() = runTest {
        //Given
        val movieEntity = MovieEntity(movieId = 1, title = "Homem de Ferro 1", imageUrl = "Url1")
        movieDao.insertMovie(movieEntity)
        val allMovies = movieDao.getMovies().first()
        val updateMovie = allMovies[0].copy(title = "Homem Aranha")

        //When
        movieDao.insertMovie(updateMovie)

        //Then
        val movies = movieDao.getMovies().first()
        assertThat(movies[0].title).contains(updateMovie.title)
    }

    @Test
    fun test_deleteMovie_should_delete_a_movie_sucessfuly() = runTest {
        //Given
        val movieEntity = MovieEntity(movieId = 1, title = "Homem de Ferro 1", imageUrl = "Url1")
        movieDao.insertMovie(movieEntity)
        val allMovies = movieDao.getMovies().first()

        //When
        movieDao.deleteMovie(movieEntity)

        //Then
        val movies = movieDao.getMovies().first()
        assertThat(movies).isEmpty()
    }
    
}