package com.chaplianski.exchangeratesviewer

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.chaplianski.exchangeratesviewer.databinding.ActivityMainBinding
import com.chaplianski.exchangeratesviewer.di.DaggerAppComponent
import com.chaplianski.exchangeratesviewer.domain.model.Currency
import com.chaplianski.exchangeratesviewer.presenter.adapter.CurrencyRateListAdapter
import com.chaplianski.exchangeratesviewer.presenter.factory.MainActivityViewModelFactory
import com.chaplianski.exchangeratesviewer.presenter.viewmodel.MainActivityViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!

    @Inject
    lateinit var mainActivityViewModelFactory: MainActivityViewModelFactory
    val mainActivityViewModel: MainActivityViewModel by viewModels { mainActivityViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerAppComponent.builder()
            .context(this)
            .build()
            .mainActivityInject(this)
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
//        setContentView(R.layout.activity_main)
        setContentView(view)

        val currencyAdapter = CurrencyRateListAdapter()
        val currencyRV = binding.rvCurrency
        val exceptionCard = binding.cvConnectionError
        val errorName = binding.tvException
        val errorMessage = binding.tvExceptionMessage
        val reloadButton = binding.btTryAgain

        currencyRV.layoutManager = LinearLayoutManager(this)
        currencyRV.adapter = currencyAdapter

        lifecycleScope.launchWhenResumed {
            mainActivityViewModel.getCurrencyList()
        }

        val loadingProgressBar = findViewById<LottieAnimationView>(R.id.pb_rate_loading)

        var checkedCurrency = ""
        var topCurrency = Currency()

        mainActivityViewModel.currencyList
            .flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
                    is MainActivityViewModel.GetCurrencyState.Loading -> {
                        loadingProgressBar.playAnimation()
                        loadingProgressBar.isVisible = true
                    }
                    is MainActivityViewModel.GetCurrencyState.Success -> {
                        loadingProgressBar.isVisible = false

                        val currentRateList = it.currencyList as MutableList<Currency>
                        if (checkedCurrency.isNotEmpty()) {
                            mainActivityViewModel.updateListCurrency(topCurrency, currentRateList)
                        } else {
                            currencyAdapter.updateData(currentRateList)
                        }

                        currencyAdapter.currencyClickListener =
                            object : CurrencyRateListAdapter.CurrencyClickListener {
                                override fun onClickItem(currency: Currency, position: Int) {
                                    checkedCurrency = currency.base.toString()
                                    topCurrency = currency
                                    mainActivityViewModel.updateListCurrency(
                                        topCurrency,
                                        currentRateList
                                    )
                                    currencyRV.scrollToPosition(0)
                                }

                                override fun onChangeText(currency: Currency) {
                                    topCurrency.rate = currency.rate
                                    mainActivityViewModel.updateListCurrency(
                                        topCurrency,
                                        currentRateList
                                    )
                                }
                            }
                    }
                    is MainActivityViewModel.GetCurrencyState.InternetError -> {
                        loadingProgressBar.isVisible = false
                        exceptionCard.isVisible = true
                        errorName.text = getString(R.string.internet_connection_error)
                        errorMessage.text = "Please, check you internet \nconnection and try again"

                    }
                    is MainActivityViewModel.GetCurrencyState.NetworkError -> {
                        loadingProgressBar.isVisible = false
                        exceptionCard.isVisible = true
                        errorName.text = getString(R.string.network_connection_error)
                        errorMessage.text = "Please, try connect again \nfrom some time"
                    }

                }
            }
            .launchIn(lifecycleScope)

        mainActivityViewModel.newCurrencyList.observe(this) {

            currencyAdapter.updateData(it)
        }

        reloadButton.setOnClickListener {
            lifecycleScope.launchWhenResumed {
                exceptionCard.isVisible = false
                mainActivityViewModel.getLoadingState()
                mainActivityViewModel.getCurrencyList()
            }
        }

    }


}
