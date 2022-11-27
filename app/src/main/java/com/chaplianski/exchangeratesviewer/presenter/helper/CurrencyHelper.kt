package com.chaplianski.exchangeratesviewer.presenter.helper

import android.content.Context
import com.chaplianski.exchangeratesviewer.R
import java.text.NumberFormat

fun getFullNameCurrency(shortName: String, context: Context): String {
    return when (shortName) {
        "USD" -> context.resources.getString(R.string.currency_usd_name)
        "EUR" -> context.resources.getString(R.string.currency_eur_name)
        "CHF" -> context.resources.getString(R.string.currency_chf_name)
        "HRK" -> context.resources.getString(R.string.currency_hrk_name)
        "MXN" -> context.resources.getString(R.string.currency_mxn_name)
        "ZAR" -> context.resources.getString(R.string.currency_zar_name)
        "CNY" -> context.resources.getString(R.string.currency_cny_name)
        "THB" -> context.resources.getString(R.string.currency_thb_name)
        "AUD" -> context.resources.getString(R.string.currency_aud_name)
        "ILS" -> context.resources.getString(R.string.currency_ils_name)
        "KRW" -> context.resources.getString(R.string.currency_krw_name)
        "BTC" -> context.resources.getString(R.string.currency_btc_name)
        "JPY" -> context.resources.getString(R.string.currency_jpy_name)
        "PLN" -> context.resources.getString(R.string.currency_pln_name)
        "GBP" -> context.resources.getString(R.string.currency_gbp_name)
        "IDR" -> context.resources.getString(R.string.currency_idr_name)
        "HUF" -> context.resources.getString(R.string.currency_huf_name)
        "PHP" -> context.resources.getString(R.string.currency_php_name)
        "TRY" -> context.resources.getString(R.string.currency_try_name)
        "RUB" -> context.resources.getString(R.string.currency_rub_name)
        "HKD" -> context.resources.getString(R.string.currency_hkd_name)
        "ISK" -> context.resources.getString(R.string.currency_isk_name)
        "DKK" -> context.resources.getString(R.string.currency_dkk_name)
        "ADA" -> context.resources.getString(R.string.currency_ada_name)
        "CAD" -> context.resources.getString(R.string.currency_cad_name)
        "MYR" -> context.resources.getString(R.string.currency_myr_name)
        "BGN" -> context.resources.getString(R.string.currency_bgn_name)
        "NOK" -> context.resources.getString(R.string.currency_nok_name)
        "RON" -> context.resources.getString(R.string.currency_ron_name)
        "SGD" -> context.resources.getString(R.string.currency_sgd_name)
        "CZK" -> context.resources.getString(R.string.currency_czk_name)
        "SEK" -> context.resources.getString(R.string.currency_sek_name)
        "NZD" -> context.resources.getString(R.string.currency_nzd_name)
        "BRL" -> context.resources.getString(R.string.currency_brl_name)
        "INR" -> context.resources.getString(R.string.currency_inr_name)
        "BYN" -> context.resources.getString(R.string.currency_byn_name)
        else -> ""
    }
}

fun getCurrencyRate(value: Number): String {
    val nf: NumberFormat = NumberFormat.getNumberInstance()
    nf.maximumFractionDigits = 5
    return nf.format(value)
}

fun getFlagImage(shortName: String): Int {
    return when (shortName) {
        "USD" -> R.drawable.ic_usd_flag
        "EUR" -> R.drawable.ic_eur_flag
        "CHF" -> R.drawable.ic_chf_flag
        "HRK" -> R.drawable.ic_hrk_flag
        "MXN" -> R.drawable.ic_mxn_flag
        "ZAR" -> R.drawable.ic_zar_flag
        "CNY" -> R.drawable.ic_cny_flag
        "THB" -> R.drawable.ic_thb_flag
        "AUD" -> R.drawable.ic_aud_flag
        "ILS" -> R.drawable.ic_ils_flag
        "KRW" -> R.drawable.ic_krw_flag
        "BTC" -> R.drawable.ic_btc_flag
        "JPY" -> R.drawable.ic_jpy_flag
        "PLN" -> R.drawable.ic_pln_flag
        "GBP" -> R.drawable.ic_gbp_flag
        "IDR" -> R.drawable.ic_idr_flag
        "HUF" -> R.drawable.ic_huf_flag
        "PHP" -> R.drawable.ic_php_flag
        "TRY" -> R.drawable.ic_try_flag
        "RUB" -> R.drawable.ic_rub_flag
        "HKD" -> R.drawable.ic_hkd_flag
        "ISK" -> R.drawable.ic_isk_flag
        "DKK" -> R.drawable.ic_dkk_flag
        "ADA" -> R.drawable.ic_ada_flag
        "CAD" -> R.drawable.ic_cad_flag
        "MYR" -> R.drawable.ic_myr_flag
        "BGN" -> R.drawable.ic_bgn_flag
        "NOK" -> R.drawable.ic_nok_flag
        "RON" -> R.drawable.ic_ron_flag
        "SGD" -> R.drawable.ic_sgd_flag
        "CZK" -> R.drawable.ic_czk_flag
        "SEK" -> R.drawable.ic_sek_flag
        "NZD" -> R.drawable.ic_nzd_flag
        "BRL" -> R.drawable.ic_brl_flag
        "INR" -> R.drawable.ic_inr_flag
        "BYN" -> R.drawable.ic_byn_flag
        else -> R.drawable.ic_usd_flag
    }
}