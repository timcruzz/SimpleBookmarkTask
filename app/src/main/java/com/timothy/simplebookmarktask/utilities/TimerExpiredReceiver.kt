package com.timothy.simplebookmarktask.utilities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class TimerExpiredReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        //NotificationUtil.showTimerExpired(context)

        PrefUtil.setTimerState(TImerUtils.TimerState.Stopped, context)
        PrefUtil.setAlarmSetTime(0, context)
    }
}