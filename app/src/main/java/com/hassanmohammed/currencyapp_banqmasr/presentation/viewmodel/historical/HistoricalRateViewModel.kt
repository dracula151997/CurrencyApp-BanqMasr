package com.hassanmohammed.currencyapp_banqmasr.presentation.viewmodel.historical

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hassanmohammed.currencyapp_banqmasr.core.utils.ApiResult
import com.hassanmohammed.currencyapp_banqmasr.domain.use_cases.GetHistoricalRatesUseCase
import com.hassanmohammed.currencyapp_banqmasr.presentation.utils.UiEvent
import com.hassanmohammed.currencyapp_banqmasr.presentation.viewmodel.historical.HistoricalRatesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HistoricalRateViewModel"

@HiltViewModel
class HistoricalRateViewModel @Inject constructor(
    private val getHistoricalRatesUseCase: GetHistoricalRatesUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<HistoricalRatesUiState> =
        MutableStateFlow(HistoricalRatesUiState())
    val uiState: StateFlow<HistoricalRatesUiState> = _uiState

    private val _uiEvent: MutableSharedFlow<UiEvent> = MutableSharedFlow()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    fun getHistoricalRates(base: String, symbol: String) = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        getHistoricalRatesUseCase(base, symbol)
            .onEach { result ->
                when (result) {
                    is ApiResult.Error -> _uiEvent.emit(UiEvent.ShowSnackBar(message = result.message))
                    is ApiResult.Success -> _uiState.update {
                        it.copy(data = result.data)
                    }
                }
            }
            .launchIn(this)
    }
}
