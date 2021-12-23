package com.example.sophossolutions.di



import com.example.sophossolutions.datasource.IDocumentDAO
import com.example.sophossolutions.datasource.IOfficeDAO
import com.example.sophossolutions.datasource.IUserDAO
import com.example.sophossolutions.util.Constans
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton






@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
///getting started with MoshiConverterFactory


    @Provides
    @Singleton
    fun providesMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }



    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(providesMoshi()))
            .baseUrl(Constans.BASE_URL)
            .build()
    }




    @Singleton
    @Provides
    fun restDataSource(retrofit: Retrofit): IUserDAO =
        retrofit.create(IUserDAO::class.java)

    @Singleton
    @Provides
    fun restDataSourceOffices(retrofit: Retrofit): IOfficeDAO =
        retrofit.create(IOfficeDAO::class.java)

    @Singleton
    @Provides
    fun restDataSourceDocuments (retrofit: Retrofit) : IDocumentDAO =
        retrofit.create(IDocumentDAO::class.java)











//    @Singleton
//    @Provides
//    @Named("BaseUrl")
//    fun provideBaseUrl() = "https://randomuser.me/api/"
//
//
//    @Singleton
//    @Provides
//
//    fun provideRetrofit(@Named("BaseUrl") baseUrl: String): Retrofit {
//        return Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl(baseUrl)
//            .build()
//    }
//
//
//    @Singleton
//    @Provides
//    fun restDataSource(retrofit: Retrofit): RestDataSource =
//        retrofit.create(RestDataSource::class.java)
//
//    @Singleton
//    @Provides
//    fun dbDataSource(@ApplicationContext context: Context): DbDataSource {
//        return Room.databaseBuilder(context, DbDataSource::class.java, "user_database")
//            .fallbackToDestructiveMigration()
//            .build()
//    }
//
//
//    @Singleton
//    @Provides
//    fun userDao(db: DbDataSource): UserDao = db.userDao()



}