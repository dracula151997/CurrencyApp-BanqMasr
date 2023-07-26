package com.hassanmohammed.currencyapp_banqmasr.domain

import com.hassanmohammed.currencyapp_banqmasr.core.utils.ApiResult
import com.hassanmohammed.currencyapp_banqmasr.data.remote.requests.currencyconverter.CurrencyConverterDto
import com.hassanmohammed.currencyapp_banqmasr.data.remote.requests.historical.HistoricalRateDto
import com.hassanmohammed.currencyapp_banqmasr.domain.models.CurrencyConverter
import com.hassanmohammed.currencyapp_banqmasr.domain.models.HistoricalRate
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun getHistoricalRates(
        date: String,
        base: String,
        symbol: String
    ) : Flow<ApiResult<HistoricalRate>>

    suspend fun convert(from: String, to: String, amount:String) : Flow<ApiResult<CurrencyConverter>>
}
