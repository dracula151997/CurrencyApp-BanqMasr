package com.hassanmohammed.currencyapp_banqmasr.presentation.viewmodel.currency

import com.hassanmohammed.currencyapp_banqmasr.domain.models.CurrencyConverter

data class CurrencyConverterUiState(
    var data: CurrencyConverter? = null,
    var currenciesRateConverters: List<CurrencyConverter>? = null,
    var isLoading: Boolean = false
) {
}
