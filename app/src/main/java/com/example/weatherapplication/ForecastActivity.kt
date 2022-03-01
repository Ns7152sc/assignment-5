package com.example.weatherapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.weatherapplication.adapter.ForecastAdapter
import com.example.weatherapplication.databinding.ActivityForecastBinding
import com.example.weatherapplication.model.ForecastList
import com.example.weatherapplication.viewModel.ForecastViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class ForecastActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForecastBinding
    private var forecastList = ArrayList<ForecastList>()
    val viewModel: ForecastViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForecastBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Forecast"
        viewModel.getForecast("55437","imperial","df5f5ad7dec319cdbd10e03799917453")
        observer()
        initAdapter()
    }

    private fun initAdapter(){
        binding.recyclerView.adapter = ForecastAdapter(forecastList)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observer(){
        viewModel.forecastData.observe(this) {
            forecastList.clear()
            forecastList.addAll(it.list)
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }
    }

}