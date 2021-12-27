package com.batdaulaptrinh.completlearningenglishapp.ui.home.tab

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.data.database.LearningAppDatabase
import com.batdaulaptrinh.completlearningenglishapp.data.sharedPreferences.SharePreferencesProvider
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentWordDayTabBinding
import com.batdaulaptrinh.completlearningenglishapp.databinding.StatisticExperienceDialogBinding
import com.batdaulaptrinh.completlearningenglishapp.others.EventDecorator
import com.batdaulaptrinh.completlearningenglishapp.repository.WordRepository
import com.batdaulaptrinh.completlearningenglishapp.ui.adapter.WordSetRecyclerAdapter
import com.batdaulaptrinh.completlearningenglishapp.ui.home.fourmode.MultipleChoiceFragment
import com.batdaulaptrinh.completlearningenglishapp.utils.Utils
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import org.threeten.bp.LocalDate
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class WordDayTabFragment : Fragment() {
    companion object {
        val KEY_AGRS_SET = "WORD_SET"
    }

    lateinit var binding: FragmentWordDayTabBinding
    lateinit var wordSetViewModel: WordSetViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_word_day_tab, container, false)
        val wordDao = LearningAppDatabase.getInstance(requireContext()).wordDao
        val wordRepository = WordRepository(wordDao)
        val wordSetViewModelFactory = WordSetViewModelFactory(requireActivity().application, wordRepository)
        wordSetViewModel = ViewModelProvider(this,wordSetViewModelFactory)[WordSetViewModel::class.java]
        binding.learnedWordTxt.text = wordSetViewModel.getNumberOfLearnedWord().toString()
        binding.experiencePointCv.setOnClickListener { cardView ->
            createExperienceDialog()
        }
        binding.learnedDayCv.setOnClickListener { cardView ->
            createStreakDialog()
        }
        //TODO fake here
        binding.recyclerView.adapter = WordSetRecyclerAdapter(Utils.getWordSet()) { wordSet ->
//            Toast.makeText(context, "CLICK AT ${wordSet.setNth}", Toast.LENGTH_LONG).show()
            findNavController().navigate(
                R.id.action_navigation_home_to_choosingModeFragment,
                bundleOf(MultipleChoiceFragment.KEY_AGRS_SET to wordSet)
            )
        }

        return binding.root

    }

    private fun createExperienceDialog() {
        val dialogBinding = DataBindingUtil.inflate<StatisticExperienceDialogBinding>(
            LayoutInflater.from(context),
            R.layout.statistic_experience_dialog,
            null,
            false
        )
        val dialog =
            AlertDialog.Builder(requireContext()).setView(dialogBinding.root)
                .create()
        when(SharePreferencesProvider(requireContext()).getCurrentLevel()){
            "BEGINNER" -> dialogBinding.resultLevelImg.setImageResource(R.drawable.level_beginner_img)
            "INTERMEDIATE" -> dialogBinding.resultLevelImg.setImageResource(R.drawable.level_intermidiate_img)
            "ADVANCED" -> dialogBinding.resultLevelImg.setImageResource(R.drawable.level_advanced_img)
            else -> Timber.e(SharePreferencesProvider(requireContext()).getCurrentLevel())
        }
        val currentSet = SharePreferencesProvider(requireContext()).getCurrentSetNth()
        val personalGoal = SharePreferencesProvider(requireContext()).getPersonalGoal()
        val numberOfWord = wordSetViewModel.getNumberOfWord()
        val numberOfLearnedWord = wordSetViewModel.getNumberOfLearnedWord()
        val maxSet = (numberOfWord - numberOfLearnedWord) / personalGoal
        dialogBinding.setUnlockTxt.text = "$currentSet/$maxSet"
        dialogBinding.learnedWordTxt.text = numberOfLearnedWord.toString()
        dialog.show()
    }

    private val onlineDateList: MutableList<String> = mutableListOf(
        "2019-01-01",
        "2021-12-8", "2021-12-9", "2021-12-10", "2021-12-15", "2021-12-16", "2021-12-19",
        "2021-12-20", "2021-12-21", "2021-12-22", "2021-12-23", "2021-12-24", "2021-12-27",
        "2021-12-28", "2021-12-29", "2021-12-30",
        "2019-01-03", "2019-01-04", "2019-01-05", "2019-01-06"
    )
    private val DATE_FORMAT = "yyyy-MM-dd"
    private var onlineColor = 0
    lateinit var calendarView: MaterialCalendarView

    private fun createStreakDialog() {
        val dialogBinding =
            DataBindingUtil.inflate<com.batdaulaptrinh.completlearningenglishapp.databinding.StatisticStreakDialogBinding>(
                layoutInflater,
                R.layout.statistic_streak_dialog,
                null,
                false
            )
        calendarView = dialogBinding.calendarView
        dialogBinding.calendarView.showOtherDates = MaterialCalendarView.SHOW_ALL

        val min = getLocalDate("2021-10-01")
        val max = getLocalDate("2021-12-30")
        calendarView.state().edit().setMinimumDate(min).setMaximumDate(max).commit()

        setEvent(onlineDateList, onlineColor)

        calendarView.invalidateDecorators()

        val dialog =
            AlertDialog.Builder(requireContext()).setView(dialogBinding.root)
                .create()
        dialog.show()
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
        if (color == onlineColor) {
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