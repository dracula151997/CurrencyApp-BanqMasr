package com.hassanmohammed.currencyapp_banqmasr.di

import com.hassanmohammed.currencyapp_banqmasr.BuildConfig
import com.hassanmohammed.currencyapp_banqmasr.core.Constants
import com.hassanmohammed.currencyapp_banqmasr.data.MainRepositoryImpl
import com.hassanmohammed.currencyapp_banqmasr.data.remote.CurrencyService
import com.hassanmohammed.currencyapp_banqmasr.domain.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideOkHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    }

    @Provides
    @Singleton
    fun provideOkHttp(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(Constants.DEFAULT_REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .callTimeout(Constants.DEFAULT_REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constants.DEFAULT_REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.FIXER_BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideCurrencyService(retrofit: Retrofit): CurrencyService {
        return retrofit.create()
    }

    @Singleton
    @Provides
    fun provideMainRepository(apiService: CurrencyService): MainRepository =
        MainRepositoryImpl(apiService)
}
