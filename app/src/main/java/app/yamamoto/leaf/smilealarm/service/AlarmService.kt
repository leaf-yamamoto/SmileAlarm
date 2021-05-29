package app.yamamoto.leaf.smilealarm.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import app.yamamoto.leaf.smilealarm.Receiver.AlarmReceiver
import app.yamamoto.leaf.smilealarm.util.Constants
import app.yamamoto.leaf.smilealarm.util.RandomintUtil

class AlarmService (private val context: Context) {
    private val alarmManager: AlarmManager? = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?


    fun setExactAlarm(timeInMillis: Long){
        setAlarm(
                timeInMillis,
                getPendingIntent(
                        getIntent().apply {
                            action = Constants.ACTION_SET_EXACT_ALARM
                            putExtra(Constants.EXTRA_EXACT_ALARM_TIME, timeInMillis)
                        }
                )
        )
    }

    fun setRepetitiveAlarm(timeInMillis: Long){
        setAlarm(
                timeInMillis,
                getPendingIntent(
                        getIntent().apply {
                            action = Constants.ACTION_SET_REPETITIVE_ALARM
                            putExtra(Constants.EXTRA_EXACT_ALARM_TIME, timeInMillis)
                        }
                )
        )
    }

    private fun setAlarm(timeInMillis: Long, pendingIntent: PendingIntent){
        alarmManager?.let {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    timeInMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    timeInMillis,
                    pendingIntent
                )

            }
        }
    }

    private fun getIntent():Intent = Intent(context, AlarmReceiver::class.java)

    private fun getPendingIntent (intent: Intent) :PendingIntent =
            PendingIntent.getBroadcast(
                    context,
                    RandomintUtil.getRandomInt(),
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
    )


}