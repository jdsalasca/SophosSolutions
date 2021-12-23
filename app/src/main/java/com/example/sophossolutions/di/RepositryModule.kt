package com.example.sophossolutions.di


import com.example.sophossolutions.repository.*

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract  class RepositryModule {

//    @Singleton
//    @Binds
//    abstract fun userRepository(repo:UserRepositoryImp): UserRepository

    @Singleton
    @Binds
    abstract fun SSuserRepository(repo:SSUserRespositoryImp): SSUserRepository

    @Singleton
    @Binds
    abstract fun SSOfficeRepository(repo:OfficeRepositoryImp): OfficeRepository

    @Singleton
    @Binds
    abstract fun SSDocumentRepository (repo: DocumentRepositoryImp) :DocumentRepository



}