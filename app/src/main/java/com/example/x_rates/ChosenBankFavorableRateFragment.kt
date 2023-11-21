package com.example.x_rates


import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.x_rates.databinding.FragmentChosenBankFavorableRateBinding
import com.example.x_rates.retrofit.model.ExchangeRatesData


class ChosenBankFavorableRateFragment : Fragment() {

    private var _binding: FragmentChosenBankFavorableRateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChosenBankFavorableRateBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupModalButtons()

        val item = arguments?.getSerializable("item") as? ExchangeRatesData

        if (item != null){
            Glide.with(binding.root)
                .load(item.icon)
                .into(binding.icBank)

            val rubCurrency = item.currency.find { it.name == "RUB" }
            rubCurrency?.let {
                binding.currencyBuyValue2.text = it.buyValue
                binding.currencySellValue2.text = it.sellValue
            }

            val usdCurrency = item.currency.find { it.name == "USD" }
            usdCurrency?.let {
                binding.currencyBuyValue3.text = it.buyValue
                binding.currencySellValue3.text = it.sellValue
            }

            val eurCurrency = item.currency.find { it.name == "EUR" }
            eurCurrency?.let {
                binding.currencyBuyValue.text = it.buyValue
                binding.currencySellValue.text = it.sellValue
            }

            val color1 = item.colors?.color1
            val color2 = item.colors?.color2
            val colors = intArrayOf(Color.parseColor(color1), Color.parseColor(color2))

            val gd = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, colors
            )
            binding.linearLayoutChosenFavorableRate.setBackgroundDrawable(gd)
        }

        binding.leftTitleReturn.setOnClickListener {
            val intent = Intent(requireContext(), FavorableRateFragment::class.java)
            startActivity(intent)
        }

        binding.icExport.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "SMT")
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }
    private fun setupModalButtons(){
        binding.buttonConversion.setOnClickListener{
            val modal = ModalBottomSheetDialog()
            requireActivity().supportFragmentManager.let { modal.show(it, ModalBottomSheetDialog.TAG) }
        }
    }
}