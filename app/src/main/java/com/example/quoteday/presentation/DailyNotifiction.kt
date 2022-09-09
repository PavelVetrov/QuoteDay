package com.example.quoteday.presentation

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.quoteday.R
import com.example.quoteday.data.network.ApiFactory
import kotlinx.coroutines.*

const val NOTIFICATION_ID = 1
const val CHANNEL = "channel"

class DailyNotification : BroadcastReceiver() {


    private val coroutineScope = CoroutineScope(Dispatchers.Main + CoroutineName("my coroutine"))


    override fun onReceive(context: Context, intent: Intent) {


        coroutineScope.launch {
             val dailyQuote = ApiFactory.apiService.getQuoteDay()
            if (dailyQuote.isSuccessful) {
                dailyQuote.body()?.let {
                    try {
                        val getQuotesDaily = it[0]
                        val massageQuote = getQuotesDaily.q
                        val massageAuthor = getQuotesDaily.a
                        delay(3000)
                        val notification = NotificationCompat.Builder(context, CHANNEL)
                            .setSmallIcon(R.drawable.ic_baseline_grade_24)
                            .setContentTitle(massageAuthor)
                            .setContentText(massageQuote)
                            .setStyle(NotificationCompat.BigTextStyle())
                            .build()
                        val manager =
                            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                        manager.notify(NOTIFICATION_ID, notification)


                    } catch (e: Exception) {

                    }
                }
            }
        }

    }


}
