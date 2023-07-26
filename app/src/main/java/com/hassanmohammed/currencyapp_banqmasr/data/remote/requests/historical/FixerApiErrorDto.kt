package com.hassanmohammed.currencyapp_banqmasr.data.remote.requests.historical

data class FixerApiErrorDto(
    val code: Int?,
    val type: String
) {

    val message: String
        get() = type.split("_").joinToString(" ", transform = String::capitalize)

}
