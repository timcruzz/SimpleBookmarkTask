package com.timothy.simplebookmarktask.utilities

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*
import java.util.concurrent.TimeUnit

class TImerUtils {
    companion object {

        // time to countdown - 1hr - 60secs
        const val TIME_COUNTDOWN: Long = 60000L
        private const val TIME_FORMAT = "%02d:%02d"
        private const val TIME_MIN_FORMAT = "%02d"

        // convert time to milli seconds
        fun Long.formatTime(): String = String.format(
            TIME_FORMAT,
            TimeUnit.MILLISECONDS.toMinutes(this),
            TimeUnit.MILLISECONDS.toSeconds(this) % 60
        )

        fun Int.toMinutes() = run { this * 60000L }

        fun Long.getRemainingMinutes(): String = String.format(
            TIME_MIN_FORMAT,
            TimeUnit.MILLISECONDS.toMinutes(this)
        )

        fun Long.getElapsedMinutes(origMinutes: Long): String {
            return (TimeUnit.MILLISECONDS.toMinutes(origMinutes) -
                        TimeUnit.MILLISECONDS.toMinutes(
                    this
                )).toString()
        }


        fun setAlarm(
            context: Context,
            alarmId: Int,
            nowSeconds: Long,
            secondsRemaining: Long
        ): Long {
            val wakeUpTime = (nowSeconds + secondsRemaining) * 1000
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, TimerExpiredReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, wakeUpTime, pendingIntent)
            PrefUtil.setAlarmSetTime(nowSeconds, context)
            return wakeUpTime
        }

        fun removeAlarm(context: Context, alarmId: Int) {
            val intent = Intent(context, TimerExpiredReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
            PrefUtil.setAlarmSetTime(0, context)
        }

        val nowSeconds: Long
            get() = Calendar.getInstance().timeInMillis / 1000

        fun formatTime(seconds: String, minutes: String): String {
            return "$minutes:$seconds"
        }

        fun Int.pad(): String {
            return this.toString().padStart(2, '0')
        }
    }

    enum class TimerState {
        Stopped, Paused, Running
    }
}

