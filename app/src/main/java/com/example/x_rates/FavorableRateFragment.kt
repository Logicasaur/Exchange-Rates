package com.example.x_rates

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.x_rates.databinding.FragmentFavorableRateBinding
import com.example.x_rates.retrofit.api.RetrofitInstance
import com.example.x_rates.retrofit.model.ExchangeRatesData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FavorableRateFragment : Fragment() {
    private var _binding: FragmentFavorableRateBinding? = null
    private val binding get() = _binding!!
    private lateinit var myadapter: ExchangeRatesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavorableRateBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupModalButtons()
        try {
            viewLifecycleOwner.lifecycleScope.launch {
                val apiService = RetrofitInstance.banksApiService
                val banks = apiService.getBanks()

                withContext(Dispatchers.Main) {

                        myadapter = ExchangeRatesAdapter(banks)

                        val onItemClickListener = object : ExchangeRatesAdapter.OnItemClickListener {
                            override fun onItemClick(item: ExchangeRatesData) {
                                Log.d("TAG_test", "onItemClick: $item ")

                                val bundle = Bundle()
                                bundle.putSerializable("item", item)

                                findNavController().navigate(
                                    R.id.action_favorableRateFragment_to_chosenBankRateFragment,
                                    bundle
                                )

                            }
                        }

                        myadapter.setOnItemClickListener(onItemClickListener)
                        binding.recyclerViewFavorableRate.adapter = myadapter


                    val favorableCurrencies = getFavorableCurrencies(banks)
                    Glide.with(binding.root)
                        .load(favorableCurrencies.icon)
                        .into(binding.icBank)

                    binding.bankName.text = favorableCurrencies.shortName
                    val color1 = favorableCurrencies.colors?.color1
                    val color2 = favorableCurrencies.colors?.color2
                    val colors = intArrayOf(Color.parseColor(color2), Color.parseColor(color1))


                    val gd = GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM, colors
                    )

                    fun dpToPx(dp: Float): Int {
                        val density = resources.displayMetrics.density
                        return (dp * density).toInt()
                    }

                    val cornerRadius = dpToPx(16F)



                    gd.cornerRadius = cornerRadius.toFloat()

                    binding.linearLayoutFavorableRate.setBackgroundDrawable(gd)
                    val currency = favorableCurrencies.currency.find { it.name == "USD" }
                    currency?.let {
                        binding.textViewRubValue.text = "1000 ${it.name}"
                        val buyValue = it.buyValue?.toDoubleOrNull()
                        if (buyValue != null) {
                            binding.textViewTjsValue.text = "${buyValue * 1000} TJS"
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

     fun getFavorableCurrencies(banks: List<ExchangeRatesData>): ExchangeRatesData {
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



    private fun setupModalButtons(){
        binding.buttonConversion.setOnClickListener{
            val modal = ModalBottomSheetDialog()
            requireActivity().supportFragmentManager.let { modal.show(it, ModalBottomSheetDialog.TAG) }
        }
    }
}