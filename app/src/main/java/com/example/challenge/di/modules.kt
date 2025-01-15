package com.example.challenge.di

import com.example.challenge.data.api.DataApi
import com.example.challenge.data.repository.IRaceRepository
import com.example.challenge.data.repository.RaceRepository
import com.example.challenge.ui.viewmodel.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .baseUrl("https://rtest.proxy.beeceptor.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(DataApi::class.java) }
    single<IRaceRepository> { RaceRepository(get()) }
    factory { MainViewModel(get()) }
}