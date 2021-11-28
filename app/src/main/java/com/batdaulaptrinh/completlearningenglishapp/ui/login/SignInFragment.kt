package com.batdaulaptrinh.completlearningenglishapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
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
        binding.signInBtn.setOnClickListener {
            if (!isValidEmail(binding.emailEdt.text.toString())) {
                Toast.makeText(context, "Invalid email format", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (binding.passwordEdt.text.toString().length < 6) {
                Toast.makeText(context,
                    "Need at least 6 characters for password",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (binding.emailEdt.text.toString() == "team6@10diem.com" && binding.passwordEdt.text.toString() == "camonco") {
                startActivity(Intent(context, IntroductionActivity::class.java))
                activity?.finish()
            }else{
                Toast.makeText(context, "Wrong email or password", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    private fun isValidEmail(target: String?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}