package com.batdaulaptrinh.completlearningenglishapp.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.media.AudioAttributes.CONTENT_TYPE_SONIFICATION
import android.media.AudioAttributes.USAGE_NOTIFICATION_RINGTONE
import android.media.RingtoneManager
import android.media.RingtoneManager.TYPE_NOTIFICATION
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.O
import android.util.Base64
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.work.*
import androidx.work.ListenableWorker.Result.success
import com.batdaulaptrinh.completlearningenglishapp.MainActivity
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.data.database.LearningAppDatabase
import com.batdaulaptrinh.completlearningenglishapp.data.sharedPreferences.SharePreferencesProvider
import com.batdaulaptrinh.completlearningenglishapp.utils.Utils
import com.bumptech.glide.Glide
import java.util.concurrent.TimeUnit

class NotifyLearningWordWorker(context: Context, params: WorkerParameters) :
    Worker(context, params) {
    override fun doWork(): Result {

        val id = inputData.getLong(Utils.ID_NOTIFY_LEARNING_WORD_WORKER, 0).toInt()
        sendNotification(id)

        return success()
    }

    private fun sendNotification(id: Int) {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(Utils.ID_NOTIFY_LEARNING_WORD_WORKER, id)

        val notificationManager =
            applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager


        val randomWordFromLearningSet =
            LearningAppDatabase.getInstance(applicationContext).wordDao.getRandomWordFromLearningSet(
                SharePreferencesProvider(applicationContext).getCurrentSetNth()
            )
        val decodedString: ByteArray = Base64.decode(randomWordFromLearningSet.thumbnail, Base64.DEFAULT)
        val bitmap =
            BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

        val titleNotification = randomWordFromLearningSet.en_word
        val subtitleNotification = randomWordFromLearningSet.definition
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0)
        val notificationBuilder =
            NotificationCompat.Builder(applicationContext, Utils.ID_NOTIFY_LEARNING_WORD_CHANNEL)
                .setLargeIcon(bitmap).setSmallIcon(IconCompat.createWithBitmap(bitmap))
                .setContentTitle(titleNotification).setContentText(subtitleNotification)
                .setDefaults(NotificationCompat.DEFAULT_ALL).setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_MAX)

        if (SDK_INT >= O) {
            notificationBuilder.setChannelId(Utils.ID_NOTIFY_LEARNING_WORD_CHANNEL)

            val ringtoneManager = RingtoneManager.getDefaultUri(TYPE_NOTIFICATION)
            val audioAttributes = AudioAttributes.Builder().setUsage(USAGE_NOTIFICATION_RINGTONE)
                .setContentType(CONTENT_TYPE_SONIFICATION).build()

            val channel =
                NotificationChannel(
                    Utils.ID_NOTIFY_LEARNING_WORD_CHANNEL,
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

    private fun vectorToBitmap(drawableId: Int): Bitmap? {
        return BitmapFactory.decodeResource(applicationContext.resources, drawableId)
    }

    private fun updateNotificationLearningWord() {
        val minuteTimeCycle =
            SharePreferencesProvider(applicationContext).getLoopNotification().toLong()
        val notificationWork =
            PeriodicWorkRequestBuilder<NotifyLearningWordWorker>(minuteTimeCycle, TimeUnit.MINUTES)
                .build()
        WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork(
                Utils.UNIQUE_NOTIFY_LEARNING_WORD_WORKER, ExistingPeriodicWorkPolicy.REPLACE,
                notificationWork
            )
    }

}