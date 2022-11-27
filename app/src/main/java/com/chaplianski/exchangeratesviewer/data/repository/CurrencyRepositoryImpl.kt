package com.chaplianski.exchangeratesviewer.data.repository

import com.chaplianski.exchangeratesviewer.data.mapDataToDomain
import com.chaplianski.exchangeratesviewer.data.service.GetCurrencyRateListApiHelper
import com.chaplianski.exchangeratesviewer.domain.model.Currency
import com.chaplianski.exchangeratesviewer.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor (
    private val getCurrencyRateListApiHelper: GetCurrencyRateListApiHelper
        ): CurrencyRepository {

    override suspend fun getCurrencyRateList(): List<Currency> {
        return getCurrencyRateListApiHelper.getAllCurrencyRateList().map { it.mapDataToDomain() }
    }
}