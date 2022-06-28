package com.example.quoteday.presentation

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.quoteday.R
import com.example.quoteday.data.RepositoryQuoteDailyImpl
import com.example.quoteday.domain.GetQuotesDailyUseCase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException

const val NOTIFICATION_ID = 1
const val CHANNEL = "channel"


class Notification: BroadcastReceiver() {

    private var repositoryQuoteDailyImpl = RepositoryQuoteDailyImpl()
    private val getQuotesDailyUseCase = GetQuotesDailyUseCase(repositoryQuoteDailyImpl)

    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context, intent: Intent) {

        GlobalScope.launch {
            val getDate = getQuotesDailyUseCase.invoke()
            if (getDate.isSuccessful) {
                getDate.body()?.let {
                    try {
                        val getQuotesDaily = it[0]
                        val massageQuote = getQuotesDaily.q
                        val massageAuthor = getQuotesDaily.a
                        delay(3000)
                        val notification = NotificationCompat.Builder(context, CHANNEL)
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .setContentTitle(massageAuthor)
                            .setContentText(massageQuote)
                            .setStyle(NotificationCompat.BigTextStyle())
                            .build()
                        val manager =
                            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                        manager.notify(NOTIFICATION_ID, notification)

                    } catch (e: HttpException) {

                    }
                }
            }
        }
    }
}
