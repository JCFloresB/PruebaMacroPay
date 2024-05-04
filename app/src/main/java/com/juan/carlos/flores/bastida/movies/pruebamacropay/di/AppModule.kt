package com.juan.carlos.flores.bastida.movies.pruebamacropay.di

import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.auth.AuthDataSource
import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.repository.LoginRepositoryImpl
import com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.repository.ILoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideLoginRepository(dataSource: AuthDataSource): ILoginRepository {

        return LoginRepositoryImpl(dataSource)

    }
}