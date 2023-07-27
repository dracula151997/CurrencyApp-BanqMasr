package com.hassanmohammed.currencyapp_banqmasr.presentation.utils

import com.hassanmohammed.currencyapp_banqmasr.core.utils.UiText

sealed class UiEvent {
    data class ShowSnackBar(val message: UiText? = null) :
        UiEvent()
}
