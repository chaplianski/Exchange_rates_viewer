package com.chaplianski.exchangeratesviewer.domain.exceptions

import androidx.annotation.StringRes

class UnknownException (@StringRes val errorMessage: Int): Exception()  {
}