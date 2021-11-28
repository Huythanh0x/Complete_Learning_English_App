package com.batdaulaptrinh.completlearningenglishapp.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentFlashCardBinding

class FlashCardFragment : Fragment() {
    lateinit var binding:FragmentFlashCardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_flash_card, container, false)
        binding.settingsBtn.setOnClickListener{
            val dialog = AlertDialog.Builder(context).setView(R.layout.flash_card_settings_dialog).create()
            dialog.show()
        }






        return binding.root

    }
}