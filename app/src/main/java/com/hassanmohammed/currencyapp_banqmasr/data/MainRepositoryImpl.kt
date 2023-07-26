package com.hassanmohammed.currencyapp_banqmasr.data

import com.hassanmohammed.currencyapp_banqmasr.data.remote.CurrencyService
import com.hassanmohammed.currencyapp_banqmasr.data.remote.safeApiCall
import com.hassanmohammed.currencyapp_banqmasr.domain.MainRepository

class MainRepositoryImpl(
    private val apiService: CurrencyService
) : MainRepository {
    override suspend fun getHistoricalRates(
        date: String,
        base: String,
        symbol: String
    ) = safeApiCall {
        apiService.getHistoricalRates(date = date, base = base, symbols = symbol)
            .toHistoricalRate()
    }

    override suspend fun convert(from: String, to: String, amount: String) =
        safeApiCall {
            apiService.convert(from = from, to = to, amount = amount).toCurrencyConverter()
        }
}
