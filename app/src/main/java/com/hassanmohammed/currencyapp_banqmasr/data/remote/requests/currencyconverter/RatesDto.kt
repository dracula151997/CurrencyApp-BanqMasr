package com.hassanmohammed.currencyapp_banqmasr.data.remote.requests.currencyconverter


import com.google.gson.annotations.SerializedName

data class RatesDto(
    @SerializedName("currency_name")
    val currencyName: String,
    @SerializedName("rate")
    val rate: String,
    @SerializedName("rate_for_amount")
    val rateForAmount: String
)
