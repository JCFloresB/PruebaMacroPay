package com.juan.carlos.flores.bastida.movies.pruebamacropay.di

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.juan.carlos.flores.bastida.movies.pruebamacropay.BuildConfig
import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.auth.AuthDataSource
import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.remote.MovieAPI
import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.remote.MoviePaging
import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.remote.dto.movies.Movie
import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.repository.LoginRepositoryImpl
import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.repository.MoviesRepositoryImpl
import com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.repository.ILoginRepository
import com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.repository.MoviesRepository
import com.juan.carlos.flores.bastida.movies.pruebamacropay.utils.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideMoviesApi(): MovieAPI {
        val logging = HttpLoggingInterceptor()
        val authInterceptor = AuthInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(authInterceptor)
        httpClient.addInterceptor(logging)
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideDataSourcePagination(moviesApi: MovieAPI): Pager<Int, Movie> {
        return Pager(PagingConfig(pageSize = 20)) {
            MoviePaging(moviesApi)
        }
    }

    @Provides
    @Singleton
    fun provideMoviesRepository(pager: Pager<Int, Movie>, moviesApi: MovieAPI): MoviesRepository {
//    fun provideMoviesRepository(moviesApi: MovieAPI): MoviesRepository {
        return MoviesRepositoryImpl(pager, moviesApi)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(dataSource: AuthDataSource): ILoginRepository {

        return LoginRepositoryImpl(dataSource)

    }
}