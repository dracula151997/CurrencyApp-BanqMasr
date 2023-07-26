package com.hassanmohammed.currencyapp_banqmasr.domain.models

data class CurrencyConverter(
    val currencyName: String,
    val amount: Float,
    val errorMessage: String
)
