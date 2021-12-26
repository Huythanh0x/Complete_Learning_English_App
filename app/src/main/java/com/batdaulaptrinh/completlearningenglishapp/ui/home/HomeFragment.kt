package com.batdaulaptrinh.completlearningenglishapp.ui.home

import android.os.Bundle
import android.text.format.DateUtils
import android.text.format.Time
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.work.*
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentHomeBinding
import com.batdaulaptrinh.completlearningenglishapp.databinding.StatisticStreakDialogBinding
import com.batdaulaptrinh.completlearningenglishapp.others.EventDecorator
import com.batdaulaptrinh.completlearningenglishapp.others.NotifyLearningWordWorker
import com.batdaulaptrinh.completlearningenglishapp.ui.adapter.HomePagerAdapter
import com.batdaulaptrinh.completlearningenglishapp.utils.Utils
import com.google.android.material.tabs.TabLayoutMediator
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import org.threeten.bp.DateTimeUtils
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val adapter = HomePagerAdapter(childFragmentManager, lifecycle)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, null, false)
        binding.viewPager2.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = "Everyday word"
                else -> tab.text = "Test"
            }
        }.attach()

        initCalendar()
        binding.includedLayout.root.visibility = View.GONE

        //faking here
        binding.titleToolBar.setOnClickListener {
            Timber.d("${LocalTime.now()}")
            val data = Data.Builder().putInt(Utils.ID_NOTIFY_LEARNING_WORD_WORKER, 0).build()
            scheduleNotification(1000, data)
        }
        notifyLearningWord(15)

        return binding.root
    }

    private fun createConstraints() = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.NOT_REQUIRED)  // if connected to WIFI
        .setRequiresCharging(false)
        .setRequiresBatteryNotLow(true)                 // if the battery is not low
        .build()

    private fun createWorkRequest(minuteTimeCycle: Long) =
        PeriodicWorkRequestBuilder<NotifyLearningWordWorker>(minuteTimeCycle, TimeUnit.MINUTES)
            .setConstraints(createConstraints())
            .build()

    private fun notifyLearningWord(minuteTimeCycle: Long) {

        /* enqueue a work, ExistingPeriodicWorkPolicy.KEEP means that if this work already exists, it will be kept
        if the value is ExistingPeriodicWorkPolicy.REPLACE, then the work will be replaced */
        WorkManager.getInstance(requireContext())
            .enqueueUniquePeriodicWork(
                Utils.UNIQUE_NOTIFY_LEARNING_WORD_WORKER, ExistingPeriodicWorkPolicy.REPLACE,
                createWorkRequest(minuteTimeCycle)
            )
    }

    private fun scheduleNotification(delay: Long, data: Data) {
        val notificationWork = OneTimeWorkRequest.Builder(NotifyLearningWordWorker::class.java)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS).setInputData(data).build()

        val instanceWorkManager = WorkManager.getInstance(requireContext())
        instanceWorkManager.beginUniqueWork(
            Utils.UNIQUE_REMINDER_WORKER,
            ExistingWorkPolicy.REPLACE,
            notificationWork
        ).enqueue()
    }

    private val onlineDateList: MutableList<String> = mutableListOf(
        "2019-01-01",
        "2021-12-8", "2021-12-9", "2021-12-10", "2021-12-15", "2021-12-16", "2021-12-19",
        "2019-01-03", "2019-01-04", "2019-01-05", "2019-01-06"
    )
    private val grayDateList: MutableList<String> = mutableListOf(
        "2019-01-09", "2019-01-10", "2019-01-11",
        "2021-12-8", "2021-12-9", "2021-12-10", "2021-12-15", "2021-12-16", "2021-12-19",
        "2019-01-24", "2019-01-25", "2019-01-26", "2019-01-27", "2019-01-28", "2019-01-29"
    )
    private val DATE_FORMAT = "yyyy-MM-dd"
    private var pink = 0
    private var gray = 1
    lateinit var calendarView: MaterialCalendarView

    fun initCalendar() {
        val dialogBinding = DataBindingUtil.inflate<StatisticStreakDialogBinding>(
            layoutInflater,
            R.layout.statistic_streak_dialog,
            null,
            false
        )

        calendarView = dialogBinding.calendarView
        calendarView.showOtherDates = MaterialCalendarView.SHOW_ALL

        val min = getLocalDate("2021-10-01")
        val max = getLocalDate("2022-03-30")
        calendarView.state().edit().setMinimumDate(min).setMaximumDate(max).commit()

        setEvent(onlineDateList, pink)
        setEvent(grayDateList, gray)

        calendarView.invalidateDecorators()
    }

    //Date Streak
    private fun setEvent(dateList: List<String?>, color: Int) {
        val localDateList: MutableList<LocalDate> = ArrayList()
        for (string in dateList) {
            val calendar = getLocalDate(string)
            if (calendar != null) {
                localDateList.add(calendar)
            }
        }
        val datesLeft: MutableList<CalendarDay> = ArrayList()
        val datesCenter: MutableList<CalendarDay> = ArrayList()
        val datesRight: MutableList<CalendarDay> = ArrayList()
        val datesIndependent: MutableList<CalendarDay> = ArrayList()
        for (localDate in localDateList) {
            var right = false
            var left = false
            for (day1 in localDateList) {
                if (localDate.isEqual(day1.plusDays(1))) {
                    left = true
                }
                if (day1.isEqual(localDate.plusDays(1))) {
                    right = true
                }
            }
            if (left && right) {
                datesCenter.add(CalendarDay.from(localDate))
            } else if (left) {
                datesLeft.add(CalendarDay.from(localDate))
            } else if (right) {
                datesRight.add(CalendarDay.from(localDate))
            } else {
                datesIndependent.add(CalendarDay.from(localDate))
            }
        }
        if (color == pink) {
            setDecor(datesCenter, R.drawable.p_center)
            setDecor(datesLeft, R.drawable.p_left)
            setDecor(datesRight, R.drawable.p_right)
            setDecor(datesIndependent, R.drawable.p_independent)
        } else {
            setDecor(datesCenter, R.drawable.g_center)
            setDecor(datesLeft, R.drawable.g_left)
            setDecor(datesRight, R.drawable.g_right)
            setDecor(datesIndependent, R.drawable.g_independent)
        }
    }

    private fun setDecor(calendarDayList: List<CalendarDay>?, drawable: Int) {
        calendarView.addDecorators(
            EventDecorator(
                context, drawable, calendarDayList
            )
        )
    }

    private fun getLocalDate(date: String?): LocalDate? {
        val sdf = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH)
        return try {
            val input = sdf.parse(date!!)
            val cal = Calendar.getInstance()
            cal.time = input!!
            LocalDate.of(
                cal[Calendar.YEAR],
                cal[Calendar.MONTH] + 1,
                cal[Calendar.DAY_OF_MONTH]
            )
        } catch (e: NullPointerException) {
            null
        } catch (e: ParseException) {
            null
        }
    }
}