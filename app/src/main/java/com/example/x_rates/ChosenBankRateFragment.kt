package com.example.x_rates

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.x_rates.retrofit.model.ExchangeRatesData


class ChosenBankRateFragment : Fragment(), ExchangeRatesAdapter.OnItemClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chosen_bank_rate, container, false)
    }

    override fun onItemClick(item: ExchangeRatesData) {
        TODO("Not yet implemented")
    }


}