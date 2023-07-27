package com.hassanmohammed.currencyapp_banqmasr.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hassanmohammed.currencyapp_banqmasr.domain.models.CurrencyConverter

class CurrencyConversionDiffUtil : DiffUtil.ItemCallback<CurrencyConverter>() {
    override fun areItemsTheSame(oldItem: CurrencyConverter, newItem: CurrencyConverter): Boolean =
        oldItem.currencyName == newItem.currencyName && oldItem.amount == newItem.amount

    override fun areContentsTheSame(
        oldItem: CurrencyConverter,
        newItem: CurrencyConverter
    ): Boolean = oldItem == newItem

}
