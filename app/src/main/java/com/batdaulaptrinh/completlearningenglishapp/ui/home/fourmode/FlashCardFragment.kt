package com.batdaulaptrinh.completlearningenglishapp.ui.home.fourmode

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.DelayBeforeMovingToNextWordDialogBinding
import com.batdaulaptrinh.completlearningenglishapp.databinding.FlashCardSettingsDialogBinding
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentFlashCardBinding
import com.batdaulaptrinh.completlearningenglishapp.databinding.TurnOffAfterDialogBinding

class FlashCardFragment : Fragment() {
    lateinit var binding: FragmentFlashCardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_flash_card, container, false)
        binding.settingsBtn.setOnClickListener {
            createSettingDialog()
        }

        binding.backwardImg.setOnClickListener {
            findNavController().popBackStack()
        }
        //TODO("CHANGE COLOR SEEKBAR")

        return binding.root

    }

    private fun createSettingDialog() {
        val dialogBinding = DataBindingUtil.inflate<FlashCardSettingsDialogBinding>(layoutInflater,
            R.layout.flash_card_settings_dialog,
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
            R.layout.turn_off_after_dialog,
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
            R.layout.delay_before_moving_to_next_word_dialog,
            null,
            false)
        val dialog = AlertDialog.Builder(context).setView(dialogBinding.root).create()
        dialogBinding.radioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
            dialog.dismiss()
        }
        dialog.show()
    }
}