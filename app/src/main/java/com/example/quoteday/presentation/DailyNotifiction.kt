package com.example.quoteday.presentation

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.quoteday.R
import com.example.quoteday.data.network.QuotesApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

const val NOTIFICATION_ID = 1
const val CHANNEL = "channel"

@AndroidEntryPoint
class DailyNotification : BroadcastReceiver() {

    @Inject
    lateinit var quotesApi: QuotesApi
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onReceive(context: Context, intent: Intent) {
        coroutineScope.launch {
            val dailyQuote = quotesApi.getQuoteDay()
            if (dailyQuote.isSuccessful) {
                dailyQuote.body()?.let {
                    val getQuotesDaily = it[0]
                    val massageQuote = getQuotesDaily.quotes
                    val massageAuthor = getQuotesDaily.author
                    val notification = NotificationCompat.Builder(context, CHANNEL)
                        .setSmallIcon(R.drawable.ic_baseline_grade_24)
                        .setContentTitle(massageAuthor)
                        .setContentText(massageQuote)
                        .setStyle(NotificationCompat.BigTextStyle())
                        .build()
                    val manager =
                        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    manager.notify(NOTIFICATION_ID, notification)
                }
            }
        }
    }
}
