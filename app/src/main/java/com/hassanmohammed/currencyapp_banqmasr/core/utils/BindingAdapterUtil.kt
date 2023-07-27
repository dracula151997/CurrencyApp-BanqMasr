package com.hassanmohammed.currencyapp_banqmasr.core.utils


import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.view.isGone
import androidx.databinding.BindingAdapter

object BindingAdapterUtil {
    @BindingAdapter("app:setItems")
    @JvmStatic
    fun AutoCompleteTextView.setItems(items: List<Any>?) {
        items?.let {
            val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, it)
            this.setAdapter(adapter)
        }
    }

    @BindingAdapter("app:setDefaultIndex")
    @JvmStatic
    fun AutoCompleteTextView.atIndex(index: Int) {
        adapter?.let {
            val itemAtPosition = it.getItem(index) as String
            this.setText(itemAtPosition, false)
        }
    }

    @BindingAdapter("app:visibleGone")
    @JvmStatic
    fun View.visibleGone(gone: Boolean) {
        isGone = gone
    }

    @BindingAdapter("app:disable")
    @JvmStatic
    fun View.disable(disable: Boolean) {
        isEnabled = !disable
    }
}
