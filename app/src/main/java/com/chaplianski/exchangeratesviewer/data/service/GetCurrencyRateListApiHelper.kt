package com.chaplianski.exchangeratesviewer.data.service

import com.chaplianski.exchangeratesviewer.R
import com.chaplianski.exchangeratesviewer.data.model.CurrencyDTO
import com.chaplianski.exchangeratesviewer.domain.exceptions.NetworkException
import retrofit2.Retrofit
import javax.inject.Inject

class GetCurrencyRateListApiHelper @Inject constructor(){

    @Inject
    lateinit var getCurrencyListRetrofit: Retrofit

    suspend fun getAllCurrencyRateList(): List<CurrencyDTO>{

        var result = emptyList<CurrencyDTO>()
        val retrofit = getCurrencyListRetrofit.create(GetCurrencyRateListApiService::class.java)
        val response = retrofit.getCurrencyList()

        when (response.code()){
            in 200..299 -> {
                result = response.body() ?: emptyList()
            }
            in 300..399 -> {
                throw NetworkException(R.string.internet_error)
            }
            in 400..499 -> {
                throw NetworkException(R.string.client_error)
            }
            in 500..599 -> {
                throw NetworkException(R.string.server_error)
            }
        }
        return result
    }
}
