package com.chaplianski.exchangeratesviewer.data.service

import com.chaplianski.exchangeratesviewer.data.model.CurrencyDTO
import retrofit2.Response
import retrofit2.http.GET


interface GetCurrencyRateListApiService{
    @GET ("myWebsiteBackend/api/currency/")
    suspend fun getCurrencyList(): Response<List<CurrencyDTO>>
}