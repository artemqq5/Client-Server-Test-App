package com.abz.abzagencytest.di

import android.content.Context
import com.abz.abzagencytest.data.UsersRepositoryImpl
import com.abz.abzagencytest.domain.UsersRepository
import com.abz.abzagencytest.domain.api.ABZAgencyAPI
import com.abz.abzagencytest.domain.use_cases.GetUsersUseCase
import com.abz.abzagencytest.domain.use_cases.NetworkStatusUseCase
import com.abz.abzagencytest.domain.use_cases.RegisterUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModules {

    @Provides
    @Singleton
    fun provideUsersRepository(api: ABZAgencyAPI): UsersRepository {
        return UsersRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideGetUsersUseCase(repository: UsersRepository): GetUsersUseCase {
        return GetUsersUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideRegisterUserUseCase(repository: UsersRepository): RegisterUserUseCase {
        return RegisterUserUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideNetworkStatusUseCase(@ApplicationContext context: Context): NetworkStatusUseCase {
        return NetworkStatusUseCase(context)
    }

}