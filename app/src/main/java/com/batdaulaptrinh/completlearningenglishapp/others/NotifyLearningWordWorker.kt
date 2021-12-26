package com.batdaulaptrinh.completlearningenglishapp.others

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.media.AudioAttributes
import android.media.AudioAttributes.CONTENT_TYPE_SONIFICATION
import android.media.AudioAttributes.USAGE_NOTIFICATION_RINGTONE
import android.media.RingtoneManager
import android.media.RingtoneManager.TYPE_NOTIFICATION
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.O
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.ListenableWorker.Result.success
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.batdaulaptrinh.completlearningenglishapp.MainActivity
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.utils.Utils

class NotifyLearningWordWorker(val context: Context, params: WorkerParameters) : Worker(context, params) {
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

        val bitmap = vectorToBitmap(R.drawable.app_logo_img)
        val titleNotification = applicationContext.getString(R.string.notification_title)
        val subtitleNotification = applicationContext.getString(R.string.notification_subtitle)
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0)
        val notificationBuilder =
            NotificationCompat.Builder(applicationContext, Utils.NOTIFICATION_CHANNEL)
                .setLargeIcon(bitmap).setSmallIcon(R.drawable.app_logo_img)
                .setContentTitle(titleNotification).setContentText(subtitleNotification)
                .setDefaults(NotificationCompat.DEFAULT_ALL).setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_MAX)

        if (SDK_INT >= O) {
            notificationBuilder.setChannelId(Utils.NOTIFICATION_CHANNEL)

            val ringtoneManager = RingtoneManager.getDefaultUri(TYPE_NOTIFICATION)
            val audioAttributes = AudioAttributes.Builder().setUsage(USAGE_NOTIFICATION_RINGTONE)
                .setContentType(CONTENT_TYPE_SONIFICATION).build()

            val channel =
                NotificationChannel(
                    Utils.NOTIFICATION_CHANNEL,
                    Utils.NOTIFICATION_NAME,
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
        return BitmapFactory.decodeResource(context.resources,drawableId)
    }
}