package app.yamamoto.leaf.smilealarm.Receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import app.yamamoto.leaf.smilealarm.service.AlarmService
import app.yamamoto.leaf.smilealarm.util.Constants
import android.text.format.DateFormat
import io.karn.notify.Notify
import java.util.*
import java.util.concurrent.TimeUnit


class AlarmReceiver :BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val timeInMillis = intent.getLongExtra(Constants.EXTRA_EXACT_ALARM_TIME, 0L)

        when (intent.action){
            Constants.ACTION_SET_EXACT_ALARM -> {
                buildNotification(context, "Set exact Time", convertDate(timeInMillis))
            }

            Constants.ACTION_SET_REPETITIVE_ALARM -> {
                val cal = Calendar.getInstance().apply {
                    this.timeInMillis = timeInMillis + TimeUnit.DAYS.toMillis(7)
                }
                AlarmService(context).setRepetitiveAlarm(cal.timeInMillis)
                buildNotification(context, "Set Repetitive Times", convertDate(timeInMillis))
            }
        }
    }

    private fun buildNotification(context: Context, title: String, message: String) {
        Notify.with(context).content{
                this.title = title
                this.text = "I got triggerd at - $message"
            }
            .show()
    }

    private fun convertDate(timeInMillis: Long): String =
        DateFormat.format ("dd/mm/yyyy hh:mm:ss", timeInMillis).toString()
}