package com.hassanmohammed.currencyapp_banqmasr.core.utils

import android.content.Context
import androidx.annotation.StringRes
import com.hassanmohammed.currencyapp_banqmasr.R

sealed class UiText {
    data class DynamicString(val value: String) : UiText()
    data class StringResource(@StringRes val id: Int) : UiText()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(id)
        }
    }

    companion object {
        fun unknownError(): UiText = StringResource(R.string.error_unknown)
    }
}

fun UiText?.orUnknownError(): UiText {
    if (this == null) return UiText.unknownError()
    return this
}
