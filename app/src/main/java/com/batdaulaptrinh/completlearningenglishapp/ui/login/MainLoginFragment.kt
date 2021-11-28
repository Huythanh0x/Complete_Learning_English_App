package com.batdaulaptrinh.completlearningenglishapp.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentMainLoginBinding

class MainLoginFragment : Fragment() {
    lateinit var binding: FragmentMainLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_login, container, false)
        binding.signInBtn.setOnClickListener{
            findNavController().navigate(R.id.action_mainLoginFragment_to_signInFragment)
        }
        binding.signUpBtn.setOnClickListener{
            findNavController().navigate(R.id.action_mainLoginFragment_to_signUpFragment)
        }
        return binding.root

    }

}