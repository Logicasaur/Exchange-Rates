package com.example.x_rates.retrofit.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        ).build()
    private val retrofit = Retrofit.Builder()
            .baseUrl("https://appsmile.ru")
        .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val banksApiService = retrofit.create(BanksApiService::class.java)
    }
