package com.chaplianski.exchangeratesviewer.data

import com.chaplianski.exchangeratesviewer.data.model.CurrencyDTO
import com.chaplianski.exchangeratesviewer.domain.model.Currency

fun CurrencyDTO.mapDataToDomain(): Currency = Currency(
    date = date,
    rate = rate,
    base = base
)