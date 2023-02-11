package com.timothy.simplebookmarktask.utilities

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.timothy.simplebookmarktask.R
import com.timothy.simplebookmarktask.config.Constants.Timer.NOTIFICATION_CHANNEL_ID
import com.timothy.simplebookmarktask.service.ServiceHelper

object NotificationUtils {
    fun provideNotificationBuilder(
        context: Context
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Daily Tasks")
            .setContentText("00:00")
            .setSmallIcon(R.drawable.ic_timer)
            .setOngoing(true)
            .addAction(0, "Stop", ServiceHelper.stopPendingIntent(context))
            .addAction(0, "Cancel", ServiceHelper.cancelPendingIntent(context))
            .setContentIntent(ServiceHelper.clickPendingIntent(context))
    }

    fun provideNotificationManager(
       context: Context
    ): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
}