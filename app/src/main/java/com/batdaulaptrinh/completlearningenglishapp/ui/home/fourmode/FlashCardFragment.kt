package com.batdaulaptrinh.completlearningenglishapp.ui.home.fourmode

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.data.database.LearningAppDatabase
import com.batdaulaptrinh.completlearningenglishapp.databinding.DelayBeforeMovingToNextWordDialogBinding
import com.batdaulaptrinh.completlearningenglishapp.databinding.FlashCardSettingsDialogBinding
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentFlashCardBinding
import com.batdaulaptrinh.completlearningenglishapp.databinding.TurnOffAfterDialogBinding
import com.batdaulaptrinh.completlearningenglishapp.model.WordSet
import com.batdaulaptrinh.completlearningenglishapp.repository.WordRepository
import com.batdaulaptrinh.completlearningenglishapp.ui.adapter.FlashCardAdapter
import com.batdaulaptrinh.completlearningenglishapp.ui.home.ChoosingModeFragment
import com.batdaulaptrinh.completlearningenglishapp.utils.Utils
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class FlashCardFragment : Fragment() {
    lateinit var binding: FragmentFlashCardBinding
    lateinit var flashCardViewModel: FlashCardViewModel
    lateinit var adapter: FlashCardAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_flash_card,
            container,
            false)
        val wordDao = LearningAppDatabase.getInstance(requireContext()).wordDao
        val wordRepository = WordRepository(wordDao)
        val flashCardViewModelFactory =
            FlashCardViewModelFactory(wordRepository, requireActivity().application)
        flashCardViewModel =
            ViewModelProvider(this, flashCardViewModelFactory)[FlashCardViewModel::class.java]
        arguments?.let {
            val setWord = it.get(ChoosingModeFragment.KEY_ARGS_SET)
            if (setWord is WordSet) {
                flashCardViewModel.getSetWordNth(setWord.setNth)
            }
        }
        adapter = FlashCardAdapter(arrayListOf(),
            moveToNextCallBack,
            moveToPreviousCallBack)
        flashCardViewModel.listWord.observe(viewLifecycleOwner,
            { listWord -> adapter.setList(listWord) })
        flashCardViewModel.currentPosition.observe(viewLifecycleOwner) { newPosition ->
            binding.viewPager2.currentItem = newPosition
            binding.flashCardViewModel = flashCardViewModel
            flashCardViewModel.changePositionPlaySound()
        }
        flashCardViewModel.isAutoPlay.observe(viewLifecycleOwner) { isAutoPlay ->
            when (isAutoPlay) {
                true -> binding.autoPlayStateImg.setImageResource(R.drawable.pause_flashcar_ic)
                false -> binding.autoPlayStateImg.setImageResource(R.drawable.play_flash_card_ic)
            }
        }
        binding.viewPager2.adapter = adapter
        binding.viewPager2.registerOnPageChangeCallback(viewPagerChangeListener)
        binding.progressSb.setOnSeekBarChangeListener(seekBarChangeListener)
        binding.backwardImg.setOnClickListener { findNavController().popBackStack() }
        binding.settingsBtn.setOnClickListener { createSettingDialog() }
        return binding.root
    }


    private fun createSettingDialog() {
        val dialogBinding =
            DataBindingUtil.inflate<FlashCardSettingsDialogBinding>(LayoutInflater.from(
                requireContext()),
                R.layout.flash_card_settings_dialog,
                null,
                false)
        val dialog = AlertDialog.Builder(requireContext()).setView(dialogBinding.root).create()
        flashCardViewModel.timeDelayLiveData.observe(viewLifecycleOwner) { timeToDelay ->
            "${timeToDelay}s".also { dialogBinding.delayBeforeMovingTimeTxt.text = it }
        }
        flashCardViewModel.timeOffLiveData.observe(viewLifecycleOwner) { timeToOff ->
            "$timeToOff minutes".also { dialogBinding.turnOffAfterTimeTxt.text = it }
        }
        flashCardViewModel.isAutoRepeatLiveData.observe(viewLifecycleOwner) { isAutoRepeat ->
            dialogBinding.autoRepeatCb.isChecked = isAutoRepeat
        }
        flashCardViewModel.isPlaySoundLiveData.observe(viewLifecycleOwner) { isPlaySound ->
            dialogBinding.playSoundCb.isChecked = isPlaySound
        }
        dialogBinding.delayBeforeMovingCl.setOnClickListener {
            showDelayMovingDialog()
        }
        dialogBinding.turnOffAfterCl.setOnClickListener {
            showDelayTurfOffDialog()
        }
        dialogBinding.autoRepeatCb.setOnCheckedChangeListener { _, isCheck ->
            flashCardViewModel.setIsAutoRepeat(isCheck)
        }
        dialogBinding.playSoundCb.setOnCheckedChangeListener { _, isCheck ->
            flashCardViewModel.setIsPlaySound(isCheck)
        }
        dialogBinding.discardBtn.setOnClickListener {
            flashCardViewModel.discardSettings()
            dialog.dismiss()
        }
        dialogBinding.saveBtn.setOnClickListener {
            flashCardViewModel.saveSettings()
            MotionToast.createColorToast(
                context as Activity,
                "save settings",
                "save settings successfully",
                MotionToastStyle.SUCCESS,
                MotionToast.GRAVITY_BOTTOM,
                Utils.SUPER_SHORT_DURATION,
                ResourcesCompat.getFont(context as Activity, R.font.helvetica_regular)
            )
            dialog.dismiss()
        }
        dialog.show()

    }

    private fun showDelayTurfOffDialog() {
        val dialogBinding = DataBindingUtil.inflate<TurnOffAfterDialogBinding>(
            LayoutInflater.from(requireContext()),
            R.layout.turn_off_after_dialog,
            null,
            false)
        val dialog = AlertDialog.Builder(requireContext()).setView(dialogBinding.root).create()
        dialogBinding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            var timeToOff = 10
            when (checkedId) {
                R.id.a1000_minutes -> timeToOff = 1000
                R.id.a5minutes -> timeToOff = 5
                R.id.a10minutes -> timeToOff = 10
                R.id.a15minutes -> timeToOff = 15
                R.id.a20minutes -> timeToOff = 20
                R.id.a30minutes -> timeToOff = 30
                R.id.a40minutes -> timeToOff = 40
                R.id.a50minutes -> timeToOff = 50
                R.id.a60minutes -> timeToOff = 60
            }
            flashCardViewModel.setTimeOff(timeToOff)
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showDelayMovingDialog() {
        val dialogBinding = DataBindingUtil.inflate<DelayBeforeMovingToNextWordDialogBinding>(
            LayoutInflater.from(requireContext()),
            R.layout.delay_before_moving_to_next_word_dialog,
            null,
            false)
        val dialog = AlertDialog.Builder(requireContext()).setView(dialogBinding.root).create()
        dialogBinding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            var timeToDelay = 5
            when (checkedId) {
                R.id.a2seconds -> timeToDelay = 2
                R.id.a3seconds -> timeToDelay = 3
                R.id.a4seconds -> timeToDelay = 4
                R.id.a5seconds -> timeToDelay = 5
                R.id.a6seconds -> timeToDelay = 6
                R.id.a7seconds -> timeToDelay = 7
                R.id.a8seconds -> timeToDelay = 8
                R.id.a9seconds -> timeToDelay = 9
                R.id.a10seconds -> timeToDelay = 10
                R.id.a11seconds -> timeToDelay = 11
                R.id.a12seconds -> timeToDelay = 12
                R.id.a13seconds -> timeToDelay = 13
                R.id.a14seconds -> timeToDelay = 14
                R.id.a15seconds -> timeToDelay = 15
                R.id.a16seconds -> timeToDelay = 16
                R.id.a17seconds -> timeToDelay = 17
                R.id.a18seconds -> timeToDelay = 18
                R.id.a19seconds -> timeToDelay = 19
                R.id.a20seconds -> timeToDelay = 20
            }
            flashCardViewModel.setTimeDelay(timeToDelay)
            dialog.dismiss()
        }
        dialog.show()
    }

    private val moveToNextCallBack: (position: Int) -> Unit =
        { flashCardViewModel.moveToNextPosition() }

    private val moveToPreviousCallBack: (position: Int) -> Unit =
        { flashCardViewModel.moveToPreviousPosition() }

    private val clickPlaySoundCallBack: () -> Unit = { flashCardViewModel.clickPlaySound() }
    private val viewPagerChangeListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            flashCardViewModel.setCurrentPosition(position)
        }
    }

    private val seekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekbar: SeekBar?, position: Int, p2: Boolean) {
            if (seekbar != null) {
                flashCardViewModel.setCurrentPosition(seekbar.progress)
            }
        }

        override fun onStartTrackingTouch(p0: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            if (seekBar != null) {
                flashCardViewModel.setCurrentPosition(seekBar.progress)
            }
        }
    }
}