package com.hassanmohammed.currencyapp_banqmasr.core.utils

sealed class ApiResult<out T>() {
    data class Success<T>(val data: T?) : ApiResult<T>()
    data class Error(val message: UiText?, val code: Int? = null) : ApiResult<Nothing>()
}
