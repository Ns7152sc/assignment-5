package com.example.weatherapplication.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapplication.network.NetworkRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductViewModelFactory @Inject constructor(private val repository: NetworkRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        try {
            val constructor = modelClass.getDeclaredConstructor(NetworkRepository::class.java)
            return constructor.newInstance(repository)
        } catch (e: Exception) {
            Log.e("ProductViewModelFactory",e.message.toString())
        }
        return super.create(modelClass)
    }
}