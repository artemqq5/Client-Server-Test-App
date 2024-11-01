package com.abz.abzagencytest.di

import com.abz.abzagencytest.domain.api.ABZAgencyAPI
import com.abz.abzagencytest.domain.api.ABZAgencyAPI.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object Retrofit2Module {

    @Provides
    @Singleton
    fun provideABZAgencyAPI(): ABZAgencyAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ABZAgencyAPI::class.java)
    }
}