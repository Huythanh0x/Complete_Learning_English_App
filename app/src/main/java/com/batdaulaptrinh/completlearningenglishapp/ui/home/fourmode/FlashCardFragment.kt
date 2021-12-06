package com.batdaulaptrinh.completlearningenglishapp.ui.home.fourmode

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.batdaulaptrinh.completlearningenglishapp.data.database.LearningAppDatabase
import com.batdaulaptrinh.completlearningenglishapp.databinding.DelayBeforeMovingToNextWordDialogBinding
import com.batdaulaptrinh.completlearningenglishapp.databinding.FlashCardSettingsDialogBinding
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentFlashCardBinding
import com.batdaulaptrinh.completlearningenglishapp.databinding.TurnOffAfterDialogBinding
import com.batdaulaptrinh.completlearningenglishapp.model.WordSet
import com.batdaulaptrinh.completlearningenglishapp.repository.WordRepository
import com.batdaulaptrinh.completlearningenglishapp.ui.adapter.FlashCardAdapter
import com.batdaulaptrinh.completlearningenglishapp.ui.home.ChoosingModeFragment
import java.util.*


class FlashCardFragment : Fragment() {
    lateinit var binding: FragmentFlashCardBinding
    lateinit var flashCardViewModel: FlashCardViewModel
    lateinit var adapter: FlashCardAdapter
    private lateinit var timer: Timer
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            com.batdaulaptrinh.completlearningenglishapp.R.layout.fragment_flash_card,
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
        adapter = FlashCardAdapter(arrayListOf(), {
            flashCardViewModel.moveToNextPosition()
        }, {
            flashCardViewModel.moveToPreviousPosition()
        })
        binding.viewPager2.adapter = adapter
        flashCardViewModel.listWord.observe(viewLifecycleOwner, { listWord ->
            adapter.setList(listWord)
        })
        flashCardViewModel.currentFlashCardPosition.observe(viewLifecycleOwner) { newPosition ->
            binding.viewPager2.setCurrentItem(newPosition, true)
            binding.progressSb.progress = newPosition
            "${(newPosition + 1)}/${flashCardViewModel.listWord.value?.size}".also {
                binding.progressTxt.text = it
            }

        }
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                flashCardViewModel.setCurrentPosition(position)
            }
        })

        binding.progressSb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekbar: SeekBar?, position: Int, p2: Boolean) {
                flashCardViewModel.setCurrentPosition(position)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                //TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                //TODO("Not yet implemented")
            }
        })

        binding.autoPlayStateImg.setOnClickListener {

            autoSlideFlashCard()
        }
        binding.settingsBtn.setOnClickListener {
            createSettingDialog()
        }
        binding.backwardImg.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

    private fun autoSlideFlashCard() {
        val runnableSlide = object : TimerTask() {
            override fun run() {
                flashCardViewModel.moveToNextPosition()
            }
        }
        if (!flashCardViewModel.isAutoPlay.value!!) {
            timer = Timer()
            timer.schedule(runnableSlide, 0, 2000)
            flashCardViewModel.isAutoPlay.value = true
        } else {
            timer.cancel()
            flashCardViewModel.isAutoPlay.value = false
        }
    }

    private fun createSettingDialog() {
        val dialogBinding = DataBindingUtil.inflate<FlashCardSettingsDialogBinding>(layoutInflater,
            com.batdaulaptrinh.completlearningenglishapp.R.layout.flash_card_settings_dialog,
            null,
            false)
        val dialog = AlertDialog.Builder(context).setView(dialogBinding.root).create()
        dialogBinding.saveBtn.setOnClickListener {
            Toast.makeText(context, "save successfully", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        dialogBinding.discardBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.delayBeforeMovingCl.setOnClickListener {
            showDelayMovingDialog()
        }
        dialogBinding.turnOffAfterCl.setOnClickListener {
            showDelayTurfOffDialog()
        }
        dialog.show()

    }

    private fun showDelayTurfOffDialog() {
        val dialogBinding = DataBindingUtil.inflate<TurnOffAfterDialogBinding>(
            layoutInflater,
            com.batdaulaptrinh.completlearningenglishapp.R.layout.turn_off_after_dialog,
            null,
            false)
        val dialog = AlertDialog.Builder(context).setView(dialogBinding.root).create()
        dialogBinding.radioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showDelayMovingDialog() {
        val dialogBinding = DataBindingUtil.inflate<DelayBeforeMovingToNextWordDialogBinding>(
            layoutInflater,
            com.batdaulaptrinh.completlearningenglishapp.R.layout.delay_before_moving_to_next_word_dialog,
            null,
            false)
        val dialog = AlertDialog.Builder(context).setView(dialogBinding.root).create()
        dialogBinding.radioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
            dialog.dismiss()
        }
        dialog.show()
    }
}