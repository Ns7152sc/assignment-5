package com.example.weatherapplication.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.model.CurrentConditions
import com.example.weatherapplication.network.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val networkRepository: NetworkRepository): ViewModel() {

    var conditionsData: MutableLiveData<CurrentConditions> = MutableLiveData()

    fun getCurrentConditions(
        zip: String,
        units: String,
        appId: String
    ) {

        viewModelScope.launch {
            networkRepository.getCurrentConditions(
                zip,units,appId
            ).run {
                onSuccess {
                    conditionsData.value = it
                }
                onFailure {
                    it.message
                }

            }
        }
    }

}