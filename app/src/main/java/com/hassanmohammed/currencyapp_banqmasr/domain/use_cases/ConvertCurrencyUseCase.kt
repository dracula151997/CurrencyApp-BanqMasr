package com.hassanmohammed.currencyapp_banqmasr.domain.use_cases

import com.hassanmohammed.currencyapp_banqmasr.core.utils.ApiResult
import com.hassanmohammed.currencyapp_banqmasr.data.remote.safeApiCall
import com.hassanmohammed.currencyapp_banqmasr.domain.MainRepository
import com.hassanmohammed.currencyapp_banqmasr.domain.models.CurrencyConverter
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class ConvertCurrencyUseCase @Inject constructor(
    private val repository: MainRepository
) {
    operator fun invoke(
        from: String,
        to: String,
        amount: String
    ): Flow<ApiResult<CurrencyConverter>> {
        var finalAmount = amount
        if (finalAmount.isEmpty() || finalAmount == "0")
            finalAmount = "1"
        return safeApiCall { repository.convert(from, to, finalAmount).toCurrencyConverter() }
    }
}
