package com.hassanmohammed.currencyapp_banqmasr.presentation.viewmodel.currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hassanmohammed.currencyapp_banqmasr.core.utils.ApiResult
import com.hassanmohammed.currencyapp_banqmasr.domain.use_cases.ConvertBaseCurrencyToOtherCurrenciesUseCase
import com.hassanmohammed.currencyapp_banqmasr.domain.use_cases.ConvertCurrencyUseCase
import com.hassanmohammed.currencyapp_banqmasr.presentation.utils.UiEvent
import com.hassanmohammed.currencyapp_banqmasr.presentation.viewmodel.currency.CurrencyConverterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "CurrencyConvertViewMode"

@HiltViewModel
class CurrencyConvertViewModel @Inject constructor(
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
    private val convertBaseCurrencyToOtherCurrenciesUseCase: ConvertBaseCurrencyToOtherCurrenciesUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<CurrencyConverterUiState> =
        MutableStateFlow(CurrencyConverterUiState())
    val uiState: StateFlow<CurrencyConverterUiState> = _uiState

    private val _uiEvent: MutableSharedFlow<UiEvent> = MutableSharedFlow()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    fun convert(from: String, to: String, amount: String) = viewModelScope.launch {
        _uiState.update {
            it.copy(isLoading = true)
        }
        convertCurrencyUseCase(from, to, amount)
            .onEach { result ->
                _uiState.update {
                    it.copy(isLoading = false)
                }
                when (result) {
                    is ApiResult.Error -> _uiEvent.emit(
                        UiEvent.ShowSnackBar(
                            message = result.message
                        )
                    )

                    is ApiResult.Success -> _uiState.update { it.copy(data = result.data) }
                }

            }.launchIn(this)
    }

    fun convert(base: String, amount: String, currencies: List<String>) = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        convertBaseCurrencyToOtherCurrenciesUseCase(base, amount, currencies)
            .onEach { result ->
                _uiState.update { it.copy(isLoading = false) }
                when (result) {
                    is ApiResult.Error -> _uiEvent.emit(
                        UiEvent.ShowSnackBar(
                            message = result.message
                        )
                    )

                    is ApiResult.Success -> _uiState.update { it.copy(currenciesRateConverters = result.data) }
                }

            }.launchIn(this)
    }

}
