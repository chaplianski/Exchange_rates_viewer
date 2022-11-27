package com.chaplianski.exchangeratesviewer.domain.exceptions

import androidx.annotation.StringRes

class InternetConnectionException (@StringRes val errorMessage: Int): Exception() {
}