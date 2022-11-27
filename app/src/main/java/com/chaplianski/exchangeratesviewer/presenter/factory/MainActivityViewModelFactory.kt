package com.chaplianski.exchangeratesviewer.presenter.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chaplianski.exchangeratesviewer.domain.usecase.GetCurrencyRateListUseCase
import com.chaplianski.exchangeratesviewer.presenter.viewmodel.MainActivityViewModel
import javax.inject.Inject



@Suppress("UNCHECKED_CAST")
class MainActivityViewModelFactory @Inject constructor(
    private val getCurrencyRateListUseCase: GetCurrencyRateListUseCase
    ) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityViewModel(getCurrencyRateListUseCase) as T
    }
}