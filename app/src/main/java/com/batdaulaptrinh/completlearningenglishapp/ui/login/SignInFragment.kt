package com.batdaulaptrinh.completlearningenglishapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentSignInBinding
import com.batdaulaptrinh.completlearningenglishapp.ui.introduction.IntroductionActivity

class SignInFragment : Fragment() {
    lateinit var binding: FragmentSignInBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        binding.signInBtn.setOnClickListener {
            startActivity(Intent(context, IntroductionActivity::class.java))
            activity?.finish()
        }

        binding.signUpTxt.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
        try {
            findNavController().popBackStack(R.id.signUpFragment, true)
        } catch (e: Exception) {
            Log.e("TAG ERROR", e.toString())
        }
        binding.backwardImg.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root

    }
}