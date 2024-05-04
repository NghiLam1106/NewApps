package com.example.do_an_co_so_3.di

import android.app.Application
import com.example.do_an_co_so_3.data.AuthRepository
import com.example.do_an_co_so_3.data.AuthRepositoryImpl
import com.example.do_an_co_so_3.data.manager.LocalUserManagerImpI
import com.example.do_an_co_so_3.domain.manager.LocalUserManager
import com.example.do_an_co_so_3.domain.usercase.AppEntryUseCases
import com.example.do_an_co_so_3.domain.usercase.ReadAppEntry
import com.example.do_an_co_so_3.domain.usercase.SaveAppEntry
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
}