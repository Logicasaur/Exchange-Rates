package com.example.x_rates.retrofit.model

import com.google.gson.annotations.SerializedName

data class ExchangeRatesData(
    @SerializedName("bank_name"    ) var bankName    : String?             = null,
    @SerializedName("short_name"    ) var shortName   : String?             = null,
    @SerializedName("colors"       ) var colors      : Colors?             = Colors(),
    @SerializedName("icon"         ) var icon        : String?             = null,
    @SerializedName("is_change"    ) var isChange    : Boolean?            = null,
    @SerializedName("android_link" ) var androidLink : String?             = null,
    @SerializedName("ios_link"     ) var iosLink     : String?             = null,
    @SerializedName("currency"     ) var currency    : ArrayList<Currency> = arrayListOf()
)

data class Colors (

    @SerializedName("color_1" ) var color1 : String? = null,
    @SerializedName("color_2" ) var color2 : String? = null

)

data class Currency (

    @SerializedName("name"       ) var name      : String? = null,
    @SerializedName("full_name"  ) var fullName  : String? = null,
    @SerializedName("flag"       ) var flag      : String? = null,
    @SerializedName("sell_value" ) var sellValue : String? = null,
    @SerializedName("buy_value"  ) var buyValue  : String? = null

)