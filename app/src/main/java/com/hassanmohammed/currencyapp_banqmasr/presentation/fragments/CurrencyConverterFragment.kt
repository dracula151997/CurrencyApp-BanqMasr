package com.hassanmohammed.currencyapp_banqmasr.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.hassanmohammed.currencyapp_banqmasr.R
import com.hassanmohammed.currencyapp_banqmasr.core.utils.BindingAdapterUtil.atIndex
import com.hassanmohammed.currencyapp_banqmasr.core.utils.BindingAdapterUtil.setItems
import com.hassanmohammed.currencyapp_banqmasr.core.utils.fragmentViewBinding
import com.hassanmohammed.currencyapp_banqmasr.core.utils.getCountries
import com.hassanmohammed.currencyapp_banqmasr.core.utils.getCurrency
import com.hassanmohammed.currencyapp_banqmasr.core.utils.hideKeyboard
import com.hassanmohammed.currencyapp_banqmasr.core.utils.showSnackbar
import com.hassanmohammed.currencyapp_banqmasr.databinding.FragmentCurrencyConverterBinding
import com.hassanmohammed.currencyapp_banqmasr.presentation.utils.UiEvent
import com.hassanmohammed.currencyapp_banqmasr.presentation.viewmodel.currency.CurrencyConvertViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CurrencyConverterFragment : Fragment(R.layout.fragment_currency_converter) {
    private val binding by fragmentViewBinding(FragmentCurrencyConverterBinding::bind)
    private val viewModel by viewModels<CurrencyConvertViewModel>()
    private var fromCurrencyCode: String = ""
    private var toCurrencyCode: String = ""
    private var fromCurrencyIdx: Int = 0
    private var toCurrencyIdx: Int = 0
    private var amount: String = "1"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        passValuesToDataBinding()
        setCountriesInSpinners()
        setListenerForViews()
        subscribeObserver()

    }

    private fun passValuesToDataBinding() {
        binding.run {
            currencyViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    private fun subscribeObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect {
                        it.data?.let { currencyConverter ->
                            if (currencyConverter.errorMessage.isNotEmpty())
                                showSnackbar(currencyConverter.errorMessage)
                        }
                    }
                }

                launch {
                    viewModel.uiEvent.collect {
                        when (it) {
                            is UiEvent.ShowSnackBar -> showSnackbar(it.message)
                        }
                    }
                }
            }
        }
    }

    private fun setListenerForViews() {
        binding.baseSelector.setOnItemClickListener { adapterView, view, position, l ->
            fromCurrencyIdx = position
        }

        binding.otherCurrencySelector.setOnItemClickListener { adapterView, view, position, l ->
            toCurrencyIdx = position
        }

        binding.baseSelector.doAfterTextChanged {
            fromCurrencyCode = getCurrency(it.toString())
        }

        binding.otherCurrencySelector.doAfterTextChanged {
            toCurrencyCode = getCurrency(it.toString())
        }

        binding.amountEt.doAfterTextChanged {
            amount = it.toString()
        }

        binding.swapBtn.setOnClickListener { swapSpinnersValues() }

        binding.convertBtn.setOnClickListener {
            hideKeyboard()
            convertCurrency()
        }

        binding.detailsBtn.setOnClickListener { navigateToHistoricalRatesScreen() }
    }

    private fun convertCurrency() {
        viewModel.convert(
            from = fromCurrencyCode,
            to = toCurrencyCode,
            amount = amount
        )
    }

    private fun setCountriesInSpinners() {
        val countries = getCountries()
        binding.baseSelector.setItems(countries)
        binding.otherCurrencySelector.setItems(countries)
    }

    private fun swapSpinnersValues() {
        swapIndices()
        binding.baseSelector.atIndex(fromCurrencyIdx)
        binding.otherCurrencySelector.atIndex(toCurrencyIdx)
    }

    private fun swapIndices() {
        val temp = fromCurrencyIdx
        fromCurrencyIdx = toCurrencyIdx
        toCurrencyIdx = temp
    }

    private fun navigateToHistoricalRatesScreen() {
        val action =
            CurrencyConverterFragmentDirections.actionCurrencyConverterFragmentToHistoricalRatesFragment(
                fromCurrencyCode,
                toCurrencyCode,
                amount
            )
        findNavController().navigate(action)
    }

    override fun onPause() {
        super.onPause()
        clearSpinnersValues()
    }

    private fun clearSpinnersValues() {
        binding.otherCurrencySelector.setText("", false)
        binding.baseSelector.setText("", false)
    }
}
