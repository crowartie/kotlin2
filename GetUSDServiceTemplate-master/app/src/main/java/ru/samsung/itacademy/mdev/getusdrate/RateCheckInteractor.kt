package ru.samsung.itacademy.mdev.getusdrate

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class RateCheckInteractor {
    val networkClient = NetworkClient()

    suspend fun requestRate(selectedCurrency: String): String {
        return withContext(Dispatchers.IO) {
            val url = MainViewModel.getUsdRateUrl(selectedCurrency)
            val result = networkClient.request(url)
            if (!result.isNullOrEmpty()) {
                parseRate(result)
            } else {
                ""
            }
        }
    }

    private fun parseRate(jsonString: String): String {
        try {
            return JSONObject(jsonString)
                .getString("RUB")
        } catch (e: Exception) {
            Log.e("RateCheckInteractor", "", e)
        }
        return ""
    }
}
