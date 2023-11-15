package com.example.x_rates.retrofit.api

import com.example.x_rates.retrofit.model.ExchangeRatesData
import retrofit2.http.GET

interface BanksApiService {
    @GET("intern-app/npcr_bank_rates_data.json")
    suspend fun getBanks(): List<ExchangeRatesData>
}