package com.chaplianski.exchangeratesviewer.domain.usecase

import com.chaplianski.exchangeratesviewer.R
import com.chaplianski.exchangeratesviewer.domain.exceptions.InternetConnectionException
import com.chaplianski.exchangeratesviewer.domain.exceptions.NetworkException
import com.chaplianski.exchangeratesviewer.domain.exceptions.UnknownException
import com.chaplianski.exchangeratesviewer.domain.model.Currency
import com.chaplianski.exchangeratesviewer.domain.repository.CurrencyRepository
import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject

class GetCurrencyRateListUseCase @Inject constructor(private var currencyRepository: CurrencyRepository) {

    suspend fun execute(): Result<List<Currency>>{
        return Result.runCatching {
            try {
                currencyRepository.getCurrencyRateList()
            }catch (e: IOException){
                throw  InternetConnectionException(R.string.internet_error)
            }catch (e: UnknownHostException){
                throw  NetworkException(R.string.server_error)
            }catch (e: ConnectException){
                throw  UnknownException(R.string.client_error)
            }
        }
    }
}