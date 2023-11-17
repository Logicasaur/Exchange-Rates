package com.example.x_rates

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.x_rates.databinding.FragmentFavorableRateBinding
import com.example.x_rates.retrofit.api.RetrofitInstance
import com.example.x_rates.retrofit.model.ExchangeRatesData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext




class FavorableRateFragment : Fragment() {
    private var _binding: FragmentFavorableRateBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ExchangeRatesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavorableRateBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val apiService = RetrofitInstance.banksApiService
                val banks = apiService.getBanks()

                withContext(Dispatchers.Main) {
                    adapter = ExchangeRatesAdapter(banks)
                    _binding?.recyclerViewFavorableRate?.adapter = adapter
                    val favorableCurrencies = getFavorableCurrencies(banks)
                    Glide.with(binding.root)
                        .load(favorableCurrencies.icon)
                        .into(binding.icBank)

                    val currency = favorableCurrencies.currency.find {it.name == "USD" }
                    currency?.let {
                        binding.textViewRubValue.text = "1000 ${it.name}"
                        val buyValue = it.buyValue?.toDoubleOrNull()
                        if (buyValue != null ) {
                            binding.textViewTjsValue.text = "${buyValue*1000} TJS"
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getFavorableCurrencies(banks: List<ExchangeRatesData>): ExchangeRatesData {
        var mostFavorableCurrency: ExchangeRatesData? = null
        var highestBuyValue = 0.0

        for (bank in banks) {
            val currency = bank.currency.find { it.name == "USD" }
            currency?.let {
                val buyValue = it.buyValue?.toDoubleOrNull()
                if (buyValue != null) {
                    if (buyValue > highestBuyValue) {
                        highestBuyValue = buyValue
                        mostFavorableCurrency = bank
                    }
                }
            }
        }
        return mostFavorableCurrency ?: ExchangeRatesData()
    }
}