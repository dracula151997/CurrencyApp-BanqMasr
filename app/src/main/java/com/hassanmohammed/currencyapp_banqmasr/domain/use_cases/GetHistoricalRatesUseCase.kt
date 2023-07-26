package com.hassanmohammed.currencyapp_banqmasr.domain.use_cases

import com.hassanmohammed.currencyapp_banqmasr.R
import com.hassanmohammed.currencyapp_banqmasr.core.utils.ApiResult
import com.hassanmohammed.currencyapp_banqmasr.core.utils.UiText
import com.hassanmohammed.currencyapp_banqmasr.core.utils.fromNowPast
import com.hassanmohammed.currencyapp_banqmasr.core.utils.toFormattedDate
import com.hassanmohammed.currencyapp_banqmasr.domain.MainRepository
import com.hassanmohammed.currencyapp_banqmasr.domain.models.HistoricalRate
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
class GetHistoricalRatesUseCase @Inject constructor(
    private val repository: MainRepository
) {
    operator fun invoke(
        base: String,
        otherCurrency: String
    ): Flow<ApiResult<List<HistoricalRate>>> = flow {
        try {
            coroutineScope {
                val historicalDay1 =
                    async {
                        repository.getHistoricalRates(
                            1.fromNowPast.toFormattedDate(),
                            base,
                            otherCurrency
                        )
                    }
                val historicalDay2 =
                    async {
                        repository.getHistoricalRates(
                            (2.fromNowPast.toFormattedDate()),
                            base,
                            otherCurrency
                        )
                    }
                val historicalDay3 =
                    async {
                        repository.getHistoricalRates(
                            3.fromNowPast.toFormattedDate(),
                            base,
                            otherCurrency
                        )
                    }
                val allHistoricalRates = mutableListOf<HistoricalRate>()
                allHistoricalRates.add(historicalDay1.await().toHistoricalRate())
                allHistoricalRates.add(historicalDay2.await().toHistoricalRate())
                allHistoricalRates.add(historicalDay3.await().toHistoricalRate())
                emit(
                    ApiResult.Success(
                        allHistoricalRates.toList()
                    )
                )
            }
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
