package com.example.weatherapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.weatherapplication.databinding.ActivityMainBinding
import com.example.weatherapplication.model.CurrentConditions
import com.example.weatherapplication.network.*
import com.example.weatherapplication.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var currentConditions: CurrentConditions
    val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Weather Application"
        initListeners()
        viewModel.getCurrentConditions("55437","imperial","df5f5ad7dec319cdbd10e03799917453")
        observer()
    }

    private fun observer(){
        viewModel.conditionsData.observe(this) {
            currentConditions = it
            initViews()
        }
    }

    private fun initListeners(){
        binding.btnForecast.setOnClickListener {
            startActivity(Intent(applicationContext, ForecastActivity::class.java))
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initViews(){
        Glide.with(applicationContext).load("http://openweathermap.org/img/wn/${currentConditions.weather[0].icon}@2x.png").into(binding.ivSun)
        binding.tvTemperature.text = "${(currentConditions.main.temp).toInt()}${resources.getString(R.string.degree)}"
        binding.tvFeelLike.text = "Feel like ${(currentConditions.main.feels_like).toInt()}${resources.getString(R.string.degree)}"
        binding.tvHighTemperature.text = "High ${(currentConditions.main.temp_max).toInt()}${resources.getString(R.string.degree)}"
        binding.tvLowTemperature.text = "Low ${(currentConditions.main.temp_min).toInt()}${resources.getString(R.string.degree)}"
        binding.tvHumidity.text = "Humidity ${currentConditions.main.humidity}%"
        binding.tvPressure.text = "Pressure ${currentConditions.main.pressure}"
        binding.tvLocation.text = currentConditions.name
    }
}