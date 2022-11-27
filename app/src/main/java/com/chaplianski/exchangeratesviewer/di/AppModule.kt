package com.chaplianski.exchangeratesviewer.di

import com.chaplianski.exchangeratesviewer.data.repository.CurrencyRepositoryImpl
import com.chaplianski.exchangeratesviewer.domain.repository.CurrencyRepository
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun provideCurrencyRepository(impl: CurrencyRepositoryImpl): CurrencyRepository = impl



}