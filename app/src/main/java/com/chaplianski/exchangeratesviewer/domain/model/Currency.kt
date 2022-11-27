package com.chaplianski.exchangeratesviewer.domain.model

data class Currency(
    val date: Int? = null,
    var rate: Float? = null,
    val base: String? = null
)

