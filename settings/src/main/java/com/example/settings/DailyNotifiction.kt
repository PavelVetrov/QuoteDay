package com.example.settings

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.settings.domain.usecase.GetQuoteDayUseCase
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
    lateinit var getQuoteDayUseCase: GetQuoteDayUseCase
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onReceive(context: Context, intent: Intent) {
        coroutineScope.launch {
            val dailyQuote = getQuoteDayUseCase()
            val massageQuote = dailyQuote.quote
            val massageAuthor = dailyQuote.author
            val notification = NotificationCompat.Builder(context, CHANNEL)
                .setSmallIcon(R.drawable.ic_grade)
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
