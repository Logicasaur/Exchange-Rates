package com.example.x_rates

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.x_rates.databinding.FragmentChosenBankRateBinding
import com.example.x_rates.retrofit.api.RetrofitInstance
import com.example.x_rates.retrofit.model.ExchangeRatesData
import kotlinx.coroutines.launch


class ChosenBankRateFragment : Fragment() {

    private var _binding: FragmentChosenBankRateBinding? = null
    private val binding get()= _binding!!

    private val intent: Intent = TODO()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChosenBankRateBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            val apiService = RetrofitInstance.banksApiService
            val banks = apiService.getBanks()
            val chosenOneBank = getChosenBank(banks)
            Glide.with(binding.root)
                .load(chosenOneBank.icon)
                .into(binding.icBank)

           val rubCurrency =  chosenOneBank.currency.find { it.name == "RUB"}
            rubCurrency?.let {
                binding.currencyBuyValue2.text = it.buyValue
                binding.currencySellValue2.text = it.sellValue
            }

            val usdCurrency =  chosenOneBank.currency.find { it.name == "USD"}
            usdCurrency?.let {
                binding.currencyBuyValue3.text = it.buyValue
                binding.currencySellValue3.text = it.sellValue
            }

            val eurCurrency =  chosenOneBank.currency.find { it.name == "EUR"}
            eurCurrency?.let {
                binding.currencyBuyValue.text = it.buyValue
                binding.currencySellValue.text = it.sellValue
            }

        }

        binding.leftTitleReturn.setOnClickListener{
            val intent = Intent(requireContext(), FavorableRateFragment::class.java)
            startActivity(intent)
        }

        binding.icExport.setOnClickListener{
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "SMT")
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }



    }
    private fun  getChosenBank(banks: List<ExchangeRatesData>) : ExchangeRatesData{
        var chosenBank : ExchangeRatesData? = null
        val localShortName = intent.getStringExtra("item")

        for(bank in banks) {

            if (bank.shortName == localShortName) {
                chosenBank = bank
            }
        }
        return chosenBank ?: ExchangeRatesData()
    }


}