package com.chaplianski.exchangeratesviewer.presenter.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chaplianski.exchangeratesviewer.domain.exceptions.InternetConnectionException
import com.chaplianski.exchangeratesviewer.domain.exceptions.NetworkException
import com.chaplianski.exchangeratesviewer.domain.model.Currency
import com.chaplianski.exchangeratesviewer.domain.usecase.GetCurrencyRateListUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val getCurrencyRateListUseCase: GetCurrencyRateListUseCase
) : ViewModel() {

    private var _currencyList = MutableStateFlow<GetCurrencyState>(GetCurrencyState.Loading())
    val currencyList = _currencyList.asStateFlow()

    private var _newCurrencyList = MutableLiveData<List<Currency>>()
    val newCurrencyList get() = _newCurrencyList


    suspend fun getCurrencyList() {
        while (true) {
            delay(5000)
            getCurrencyRateListUseCase.execute().fold({
                _currencyList.emit(GetCurrencyState.Success(it))
            }, {
                when (it) {
                    is NetworkException -> {
                        _currencyList.emit(GetCurrencyState.NetworkError(it.errorMessage))
                    }
                    is InternetConnectionException -> {
                        _currencyList.emit(GetCurrencyState.InternetError(it.errorMessage))
                    }
                    else -> {
                    }
                }
            })
        }

    }

    fun updateListCurrency(
        currency: Currency,
        currentRateList: MutableList<Currency>
    ) {
        val currencyList = mutableListOf<Currency>()
        currencyList.add(currency)

        currentRateList.forEach { curr ->
            if (curr.base != currency.base) {
                curr.rate = curr.rate?.let { currency.rate?.times(it) }
                currencyList.add(curr)
            }
        }
        _newCurrencyList.postValue(currencyList)
    }

    suspend fun getLoadingState() {
        _currencyList.emit(GetCurrencyState.Loading())
    }


    sealed class GetCurrencyState() {
        class Loading : GetCurrencyState()
        class Success(val currencyList: List<Currency>) : GetCurrencyState()
        class InternetError(@StringRes val exception: Int) : GetCurrencyState()
        class NetworkError(@StringRes val exception: Int) : GetCurrencyState()
    }
}