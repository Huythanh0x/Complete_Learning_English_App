package com.batdaulaptrinh.completlearningenglishapp.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.batdaulaptrinh.completlearningenglishapp.MainActivity
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.data.sharedPreferences.SharePreferencesProvider
import com.batdaulaptrinh.completlearningenglishapp.utils.Utils
import timber.log.Timber
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit


class ReminderWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        val id = inputData.getInt(Utils.ID_REMINDER_WORKER, 1)
        if (Utils.isAppRunning(applicationContext)) return Result.failure()
        sendNotification(id)
        scheduleForNextDay()
        return Result.success()
    }

    private fun scheduleForNextDay() {
        val workManager = WorkManager.getInstance(applicationContext)

        val preferLearningTime =
            SharePreferencesProvider(applicationContext).getPreferLearningTime()
        val alarmTime = LocalTime.of(
            preferLearningTime.split(":")[0].toInt(),
            preferLearningTime.split(":")[1].toInt()
        )
        var now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        val nowTime = now.toLocalTime()
        // if same time, schedule for next day as well
        // if today's time had passed, schedule for next day
        if (nowTime == alarmTime || nowTime.isAfter(alarmTime)) {
            now = now.plusDays(1)
        }
        now = now.withHour(alarmTime.hour)
            .withMinute(alarmTime.minute) // .withSecond(alarmTime.second).withNano(alarmTime.nano)
        val duration = Duration.between(LocalDateTime.now(), now)

        Timber.d("runAfter=${duration.seconds}s")

        // optional data
        val data = workDataOf(Utils.DATA_KEY_REMINDER_NOTIFICATION to "Timer 01")

        val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(duration.seconds, TimeUnit.SECONDS)
            // .setConstraints(constraints)
            .setInputData(data) // optional
            .build()

        workManager.enqueueUniqueWork(
            Utils.UNIQUE_REMINDER_WORKER,
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
    }


    private fun sendNotification(id: Int) {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(Utils.ID_NOTIFY_LEARNING_WORD_WORKER, id)

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val bitmap = vectorToBitmap(R.drawable.app_logo_img)
        val titleNotification = "IT IS TIME TO LEARN NEW WORD"
        val subtitleNotification = "Comeback to complete your goal today"
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0)
        val notificationBuilder =
            NotificationCompat.Builder(applicationContext, Utils.ID_REMINDER_CHANNEL)
                .setLargeIcon(bitmap).setSmallIcon(R.drawable.app_logo_img)
                .setContentTitle(titleNotification).setContentText(subtitleNotification)
                .setDefaults(NotificationCompat.DEFAULT_ALL).setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_MAX)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuilder.setChannelId(Utils.ID_REMINDER_CHANNEL)

            val ringtoneManager = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val audioAttributes =
                AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build()

            val channel =
                NotificationChannel(
                    Utils.ID_REMINDER_CHANNEL,
                    Utils.REMIND_NOTIFICATION_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                )

            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            channel.setSound(ringtoneManager, audioAttributes)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(id, notificationBuilder.build())
    }


    fun cancel() {
        Timber.d("cancel")
        val workManager = WorkManager.getInstance()
        workManager.cancelUniqueWork(Utils.UNIQUE_REMINDER_WORKER)
    }

    private fun vectorToBitmap(drawableId: Int): Bitmap? {
        return BitmapFactory.decodeResource(applicationContext.resources, drawableId)
    }


    private fun updateNotificationLearningWord() {
        val minuteTimeCycle =
            SharePreferencesProvider(applicationContext).getLoopNotification().toLong()
        val notificationWork =
            PeriodicWorkRequestBuilder<ReminderWorker>(minuteTimeCycle, TimeUnit.MINUTES)
                .build()
        WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork(
                Utils.UNIQUE_REMINDER_WORKER, ExistingPeriodicWorkPolicy.REPLACE,
                notificationWork
            )
    }
}
