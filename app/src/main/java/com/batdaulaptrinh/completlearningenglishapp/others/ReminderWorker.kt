package com.batdaulaptrinh.completlearningenglishapp.others

import android.content.Context
import androidx.work.*
import com.batdaulaptrinh.completlearningenglishapp.data.sharedPreferences.SharePreferencesProvider
import com.batdaulaptrinh.completlearningenglishapp.utils.Utils
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.temporal.ChronoUnit
import timber.log.Timber
import java.net.SocketException
import java.util.concurrent.TimeUnit

class ReminderWorker(val context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {
    // optional - send parameter to worker
    // private const val RESULT_ID = "id"

    fun scheduleForNextDay() {
        val workManager = WorkManager.getInstance()

        val preferLearningTime = SharePreferencesProvider(context).getPreferLearningTime()
        val alarmTime = LocalTime.of(preferLearningTime.split(":")[0].toInt(), preferLearningTime.split(":")[1].toInt())
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

        // optional constraints
        /*
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
         */

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

    fun cancel() {
        Timber.d("cancel")
        val workManager = WorkManager.getInstance()
        workManager.cancelUniqueWork(Utils.UNIQUE_REMINDER_WORKER)
    }


    override suspend fun doWork(): Result{
        val name = inputData.getString(Utils.DATA_KEY_REMINDER_NOTIFICATION)
        Timber.d("doWork=$name")

        var isScheduleNext = true
        try {
            // do something

            // possible to return result
            // val data = workDataOf(RESULT_ID to 1)
            // Result.success(data)

            Result.success()
        } catch (e: Exception) {
            // only retry 3 times
            if (runAttemptCount > 3) {
                Timber.d("runAttemptCount=$runAttemptCount, stop retry")
                return Result.success()
            }

            // retry if network failure, else considered failed
            return when (e.cause) {
                is SocketException -> {
                    Timber.e(e.toString(), e.message)
                    isScheduleNext = false
                    Result.retry()
                }
                else -> {
                    Timber.e(e)
                    Result.failure()
                }
            }
        } finally {
            // only schedule next day if not retry, else it will overwrite the retry attempt
            // - because we use uniqueName with ExistingWorkPolicy.REPLACE
            if (isScheduleNext) {
                scheduleForNextDay() // schedule for next day
            }
        }
        return Result.failure()
    }
}