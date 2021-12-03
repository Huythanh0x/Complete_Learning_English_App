package com.batdaulaptrinh.completlearningenglishapp.ui.home.fourmode

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.batdaulaptrinh.completlearningenglishapp.data.database.LearningAppDatabase
import com.batdaulaptrinh.completlearningenglishapp.databinding.DelayBeforeMovingToNextWordDialogBinding
import com.batdaulaptrinh.completlearningenglishapp.databinding.FlashCardSettingsDialogBinding
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentFlashCardBinding
import com.batdaulaptrinh.completlearningenglishapp.databinding.TurnOffAfterDialogBinding
import com.batdaulaptrinh.completlearningenglishapp.model.WordSet
import com.batdaulaptrinh.completlearningenglishapp.repository.WordRepository
import com.batdaulaptrinh.completlearningenglishapp.ui.home.ChoosingModeFragment
import kotlin.random.Random


class FlashCardFragment : Fragment() {
    lateinit var binding: FragmentFlashCardBinding
    lateinit var flashCardViewModel: FlashCardViewModel
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
        val flashCardViewModelFactory = FlashCardViewModelFactory(wordRepository)
        flashCardViewModel =
            ViewModelProvider(this, flashCardViewModelFactory)[FlashCardViewModel::class.java]
        arguments?.let {
            val setWord = it.get(ChoosingModeFragment.KEY_ARGS_SET)
            if (setWord is WordSet) {
                flashCardViewModel.getSetWordNth(setWord.setNth)
            }
        }
        //TODO("CHANGE COLOR SEEKBAR")
        //TODO FAKE HERE
        flashCardViewModel.listWord.observe(viewLifecycleOwner, { lkistWord ->
            binding.word = listWord[Random.nextInt(20)]
            Log.d("LIST FLASH CARD TAG", listWord.size.toString())
        })
        binding.settingsBtn.setOnClickListener {
            createSettingDialog()
        }
        binding.backwardImg.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun createSettingDialog() {
        val dialogBinding = DataBindingUtil.inflate<FlashCardSettingsDialogBinding>(layoutInflater,
            com.batdaulaptrinh.completlearningenglishapp.R.layout.flash_card_settings_dialog,
            null,
            false)
        //TODO("choose correct answer")
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
        //TODO("background")
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