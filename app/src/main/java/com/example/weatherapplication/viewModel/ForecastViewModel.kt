package com.example.weatherapplication.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.model.Forecast
import com.example.weatherapplication.network.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(private val networkRepository: NetworkRepository): ViewModel()  {


    var forecastData: MutableLiveData<Forecast> = MutableLiveData()

    fun getForecast(
        zip: String,
        units: String,
        appId: String
    ) {

        viewModelScope.launch {
            networkRepository.getForeCast(
                zip,units,appId
            ).run {
                onSuccess {
                    forecastData.value = it
                }
                onFailure {

                }

            }
        }
    }
}