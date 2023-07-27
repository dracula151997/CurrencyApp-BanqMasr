package com.hassanmohammed.currencyapp_banqmasr.presentation.viewmodel.historical

import com.hassanmohammed.currencyapp_banqmasr.domain.models.HistoricalRate

data class HistoricalRatesUiState(
    val data: List<HistoricalRate>? = emptyList(),
    val isLoading: Boolean = false
) {
    val errorMessage: String get() = data?.firstOrNull()?.errorMessage ?: ""
}
