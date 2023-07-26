package com.hassanmohammed.currencyapp_banqmasr.core.utils

sealed class ApiResult<T>(val data: T? = null, val uiText: UiText? = null) {
    class Success<T>(data: T?) : ApiResult<T>(data)
    data class Error(val message: UiText?, val code: Int? = null) : ApiResult<Nothing>()
}
