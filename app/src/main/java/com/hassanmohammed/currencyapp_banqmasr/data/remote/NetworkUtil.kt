package com.hassanmohammed.currencyapp_banqmasr.data.remote

import com.hassanmohammed.currencyapp_banqmasr.R
import com.hassanmohammed.currencyapp_banqmasr.core.utils.ApiResult
import com.hassanmohammed.currencyapp_banqmasr.core.utils.UiText
import com.hassanmohammed.currencyapp_banqmasr.domain.models.HistoricalRate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException

fun <T> safeApiCall(
    call: suspend () -> T
): Flow<ApiResult<T>> = flow {
    try {
        val response = call()
        emit(ApiResult.Success(response))
    } catch (e: Exception) {
        when (e) {
            is ConnectException -> ApiResult.Error(UiText.StringResource(R.string.error_check_your_internet_connection))
            is SocketTimeoutException -> ApiResult.Error(UiText.StringResource(R.string.error_connection_timeout))
            is IOException -> ApiResult.Error(UiText.StringResource(R.string.error_check_your_internet_connection))
            else -> ApiResult.Error(UiText.unknownError())
        }
    }
}
