package com.chaplianski.exchangeratesviewer.domain.repository

import com.chaplianski.exchangeratesviewer.domain.model.Currency

interface CurrencyRepository {

    suspend fun getCurrencyRateList(): List<Currency>
}