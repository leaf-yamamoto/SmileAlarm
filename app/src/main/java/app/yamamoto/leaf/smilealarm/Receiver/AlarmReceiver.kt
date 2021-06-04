package app.yamamoto.leaf.smilealarm.Receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import app.yamamoto.leaf.smilealarm.service.AlarmService
import app.yamamoto.leaf.smilealarm.util.Constants
import android.text.format.DateFormat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import app.yamamoto.leaf.smilealarm.CameraActivity
import app.yamamoto.leaf.smilealarm.MainActivity
import io.karn.notify.Notify
import java.util.*
import java.util.concurrent.TimeUnit


class AlarmReceiver :BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val timeInMillis = intent.getLongExtra(Constants.EXTRA_EXACT_ALARM_TIME, 0L)

        when (intent.action){
            Constants.ACTION_SET_EXACT_ALARM -> {
                val toCameraIntent = Intent(context, CameraActivity::class.java)
                startActivity(context, toCameraIntent)
            }

            Constants.ACTION_SET_REPETITIVE_ALARM -> {
                val cal = Calendar.getInstance().apply {
                    this.timeInMillis = timeInMillis + TimeUnit.DAYS.toMillis(7)
                }
                AlarmService(context).setRepetitiveAlarm(cal.timeInMillis)
                val toCameraIntent= Intent(context, CameraActivity::class.java)
                startActivity(context, toCameraIntent)
            }
        }
    }
     }