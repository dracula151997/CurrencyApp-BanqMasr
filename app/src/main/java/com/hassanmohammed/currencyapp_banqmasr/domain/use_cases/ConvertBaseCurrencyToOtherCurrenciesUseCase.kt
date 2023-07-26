package com.hassanmohammed.currencyapp_banqmasr.domain.use_cases

import com.hassanmohammed.currencyapp_banqmasr.R
import com.hassanmohammed.currencyapp_banqmasr.core.utils.ApiResult
import com.hassanmohammed.currencyapp_banqmasr.core.utils.UiText
import com.hassanmohammed.currencyapp_banqmasr.domain.MainRepository
import com.hassanmohammed.currencyapp_banqmasr.domain.models.CurrencyConverter
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

@ViewModelScoped
class ConvertBaseCurrencyToOtherCurrenciesUseCase @Inject constructor(
    private val repository: MainRepository
) {
    operator fun invoke(
        base: String,
        amount: String,
        currencies: List<String>
    ): Flow<ApiResult<List<CurrencyConverter>>> = flow {
        var finalAmount = amount
        if (finalAmount.isEmpty() || finalAmount == "0")
            finalAmount = "1"

        try {
            val allHistoricalRates = mutableListOf<CurrencyConverter>()
            coroutineScope {
                currencies.forEach { currency ->
                    val api = async { repository.convert(base, currency, finalAmount) }
                    allHistoricalRates.add(api.await().toCurrencyConverter())
                }
            }
            emit(ApiResult.Success(allHistoricalRates.toList()))
        } catch (e: Exception) {
            when (e) {
                is ConnectException -> ApiResult.Error(UiText.StringResource(R.string.error_check_your_internet_connection))
                is SocketTimeoutException -> ApiResult.Error(UiText.StringResource(R.string.error_connection_timeout))
                is IOException -> ApiResult.Error(UiText.StringResource(R.string.error_check_your_internet_connection))
                else -> ApiResult.Error(UiText.unknownError())
            }
        }


    }
}
