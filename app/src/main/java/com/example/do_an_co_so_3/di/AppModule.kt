package com.example.do_an_co_so_3.di

import android.app.Application
import com.example.do_an_co_so_3.data.AuthRepository
import com.example.do_an_co_so_3.data.AuthRepositoryImpl
import com.example.do_an_co_so_3.data.manager.LocalUserManagerImpI
import com.example.do_an_co_so_3.data.remote.NewsApi
import com.example.do_an_co_so_3.data.repository.NewsRepositoryImpl
import com.example.do_an_co_so_3.domain.manager.LocalUserManager
import com.example.do_an_co_so_3.domain.repository.NewsRepository
import com.example.do_an_co_so_3.domain.usercase.app_entry.AppEntryUseCases
import com.example.do_an_co_so_3.domain.usercase.app_entry.ReadAppEntry
import com.example.do_an_co_so_3.domain.usercase.app_entry.SaveAppEntry
import com.example.do_an_co_so_3.domain.usercase.news.GetNews
import com.example.do_an_co_so_3.domain.usercase.news.NewsUseCase
import com.example.do_an_co_so_3.domain.usercase.news.SearchNews
import com.example.do_an_co_so_3.util.Constants.BASE_URL
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesFirebaseAuth()  = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesRepositoryImpl(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun providelocalUserManager(
        application: Application
    ): LocalUserManager = LocalUserManagerImpI(application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager: LocalUserManager
    ) = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager)
    )

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsApi: NewsApi
    ): NewsRepository = NewsRepositoryImpl(newsApi)

    @Provides
    @Singleton
    fun provideNewsUseCase(
        newsRepository: NewsRepository
    ): NewsUseCase {
        return NewsUseCase(
            getNews = GetNews(newsRepository),
            searchNews = SearchNews(newsRepository)
        )
    }
}