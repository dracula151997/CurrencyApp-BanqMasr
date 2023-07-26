package com.hassanmohammed.currencyapp_banqmasr.domain.models

data class HistoricalRate(
    val date: String = "",
    val base: String = "",
    val rate: Float? = 0f,
    val errorMessage: String? = null
)
