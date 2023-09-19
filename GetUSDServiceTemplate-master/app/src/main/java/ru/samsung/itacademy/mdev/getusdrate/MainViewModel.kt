package ru.samsung.itacademy.mdev.getusdrate

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel : ViewModel() {
    val usdRate = MutableLiveData<String>()
    private val rateCheckInteractor = RateCheckInteractor()
    val selectedCurrency = MutableLiveData<String>()
    val quoteHistory = MutableLiveData<List<Quote>>()

    fun onCreate() {
        selectedCurrency.value = "BTC"
        refreshRate()
    }

    fun onRefreshClicked() {
        refreshRate()
    }

    fun refreshRate() {
        GlobalScope.launch(Dispatchers.Main) {
            val currency = selectedCurrency.value
            val rate = currency?.let { rateCheckInteractor.requestRate(it) }
            Log.d(TAG, "usdRate = $rate")
            usdRate.value = rate
            val history = quoteHistory.value?.toMutableList() ?: mutableListOf()
            rate?.let { Quote(Date(), it, currency) }?.let { history.add(it) }
            quoteHistory.value = history
        }
    }

    companion object {
        const val TAG = "MainViewModel"

        fun getUsdRateUrl(selectedCurrency: String): String {
            return "https://min-api.cryptocompare.com/data/price?fsym=$selectedCurrency&tsyms=RUB"
        }
    }
}