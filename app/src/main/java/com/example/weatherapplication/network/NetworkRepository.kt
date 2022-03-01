package com.example.weatherapplication.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetworkRepository @Inject constructor(var apiService: Api) {

    private val dispatcher = Dispatchers.IO

    suspend fun getForeCast(
        zip: String,
        units: String,
        appId: String
    ) = withContext(dispatcher) {
        safeApiCall {
            Result.success(apiService.getForecast(zip,units,appId))
        }
    }

    suspend fun getCurrentConditions(
        zip: String,
        units: String,
        appId: String
    ) = withContext(dispatcher) {
        safeApiCall {
            Result.success(apiService.getCurrentConditions(zip,units,appId))
        }
    }
}