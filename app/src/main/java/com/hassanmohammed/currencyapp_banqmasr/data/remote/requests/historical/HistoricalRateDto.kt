package com.hassanmohammed.currencyapp_banqmasr.data.remote.requests.historical

import com.hassanmohammed.currencyapp_banqmasr.domain.models.HistoricalRate


data class HistoricalRateDto(
    val success: Boolean? = false,
    val historical: Boolean? = false,
    val date: String? = "",
    val timestamp: Int? = 0,
    val base: String? = "",
    val rates: Map<String, Float>?,
    val error: FixerApiErrorDto? = null
) {
    fun toHistoricalRate() =
        HistoricalRate(date.orEmpty(), base.orEmpty(), rates?.values?.first(), error?.message)
}
