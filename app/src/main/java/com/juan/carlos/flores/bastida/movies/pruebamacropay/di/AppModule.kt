package com.juan.carlos.flores.bastida.movies.pruebamacropay.di

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.juan.carlos.flores.bastida.movies.pruebamacropay.BuildConfig
import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.auth.AuthDataSource
import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.local.UserPreferences
import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.remote.MovieAPI
import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.remote.MoviePaging
import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.remote.dto.movies.Movie
import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.repository.LoginRepositoryImpl
import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.repository.MoviesRepositoryImpl
import com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.repository.ILoginRepository
import com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.repository.MoviesRepository
import com.juan.carlos.flores.bastida.movies.pruebamacropay.utils.AuthInterceptor
import com.juan.carlos.flores.bastida.movies.pruebamacropay.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun providePreferences(@ApplicationContext context: Context) = UserPreferences(context)

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
        return Pager(PagingConfig(pageSize = Constants.MOVIE_PAGING_PAGE_SIZE)) {
            MoviePaging(moviesApi)
        }
    }

    @Provides
    @Singleton
    fun provideMoviesRepository(pager: Pager<Int, Movie>, moviesApi: MovieAPI): MoviesRepository {
        return MoviesRepositoryImpl(pager, moviesApi)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(dataSource: AuthDataSource, preferences: UserPreferences): ILoginRepository {
        return LoginRepositoryImpl(dataSource, preferences)
    }
}