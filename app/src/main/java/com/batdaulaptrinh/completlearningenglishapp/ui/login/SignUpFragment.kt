package com.batdaulaptrinh.completlearningenglishapp.ui.login

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentSignUpBinding
import com.batdaulaptrinh.completlearningenglishapp.databinding.SignUpSuccessfullyDialogBinding

class SignUpFragment : Fragment() {
    lateinit var binding: FragmentSignUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        binding.signUpTxt.setOnClickListener { createSignUpSuccessfullyDialog() }
        binding.backwardImg.setOnClickListener { findNavController().popBackStack() }

        return binding.root
    }

    private fun createSignUpSuccessfullyDialog() {
        val dialogBinding = DataBindingUtil.inflate<SignUpSuccessfullyDialogBinding>(layoutInflater,
            R.layout.sign_up_successfully_dialog,
            null,
            false)

        val dialog = AlertDialog.Builder(context).setView(dialogBinding.root).create()
        dialogBinding.signInBtn.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
            dialog.dismiss()
        }
        dialog.show()
    }

}