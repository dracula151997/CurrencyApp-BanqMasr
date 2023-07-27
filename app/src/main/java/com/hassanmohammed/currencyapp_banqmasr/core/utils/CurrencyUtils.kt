package com.hassanmohammed.currencyapp_banqmasr.core.utils

import java.util.Currency
import java.util.Locale

fun getCountries(): List<String> {
    val locales = Locale.getAvailableLocales()
    val countriesList = ArrayList<String>()
    for (locale in locales) {
        val country = locale.displayCountry
        if (country.isNotEmpty() && !countriesList.contains(country))
            countriesList.add(country)
    }

    return countriesList.sorted()
}

fun getISOCountries(): List<String> {
    val locales = Locale.getISOCountries()
    val isoCounties = ArrayList<String>()
    for (isoCountry in locales) {
        if (isoCountry.isNotEmpty() && !isoCounties.contains(isoCountry))
            isoCounties.add(isoCountry)
    }

    return isoCounties.sorted()
}

fun getCountryCode(countryName: String) =
    Locale.getISOCountries().find { Locale("", it).displayCountry == countryName }

fun getCurrency(countryName: String): String {
    val countryCode = getCountryCode(countryName)
    val locales = Locale.getAvailableLocales()
    for (i in locales.indices) {
        if (locales[i].country == countryCode) return Currency.getInstance(locales[i]).currencyCode
    }
    return ""
}
