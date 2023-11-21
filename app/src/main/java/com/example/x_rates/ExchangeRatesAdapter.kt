package com.example.x_rates

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.x_rates.databinding.ItemFavorableRateBinding
import com.example.x_rates.retrofit.model.ExchangeRatesData


class ExchangeRatesAdapter(private val banks: List<ExchangeRatesData>) : RecyclerView.Adapter<ExchangeRatesAdapter.CardViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    @SuppressLint("NotConstructor")
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }
    class CardViewHolder(private val binding: ItemFavorableRateBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ExchangeRatesData) {
            Glide.with(binding.root)
                .load(item.icon)
                .into(binding.icBank)
            binding.bankName.text = item.shortName
            val rubCurrency = item.currency.find { it.name == "USD" }
            Log.d("TAG_test", "bind: $item, $rubCurrency")
            rubCurrency?.let {
                binding.currencyValue.text = "1 ${it.name}"
                binding.currencySellValue.text = it.sellValue
                binding.currencyBuyValue.text = it.buyValue
            }

        }

    }

    interface OnItemClickListener {
        fun onItemClick(item: ExchangeRatesData?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemBinging =
            ItemFavorableRateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(itemBinging)    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val item = banks[position]
        holder.bind(item)
        holder.itemView.setOnClickListener{
            onItemClickListener?.onItemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return banks.size
    }

}