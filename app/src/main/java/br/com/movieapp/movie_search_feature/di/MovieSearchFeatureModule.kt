package br.com.movieapp.movie_search_feature.di

import br.com.movieapp.core.data.remote.MovieService
import br.com.movieapp.movie_search_feature.data.repository.MovieSearchRepositoryImpl
import br.com.movieapp.movie_search_feature.data.source.MovieSearchRemoteDataSourceImpl
import br.com.movieapp.movie_search_feature.domain.repository.MovieSearchRepository
import br.com.movieapp.movie_search_feature.domain.source.MovieSearchRemoteDataSource
import br.com.movieapp.movie_search_feature.domain.usecase.GetMovieSearchUseCase
import br.com.movieapp.movie_search_feature.domain.usecase.GetMovieSearchUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieSearchFeatureModule {

    @Provides
    @Singleton
    fun provideMovieSearchDataSource(service: MovieService): MovieSearchRemoteDataSource{
        return MovieSearchRemoteDataSourceImpl(service = service)
    }

    @Provides
    @Singleton
    fun provideMovieSearchRepository(remoteDataSource: MovieSearchRemoteDataSource): MovieSearchRepository{
        return MovieSearchRepositoryImpl(remoteDataSource = remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideGetMovieSearchUseCase(repository: MovieSearchRepository): GetMovieSearchUseCase{
        return GetMovieSearchUseCaseImpl(repository = repository)
    }
}