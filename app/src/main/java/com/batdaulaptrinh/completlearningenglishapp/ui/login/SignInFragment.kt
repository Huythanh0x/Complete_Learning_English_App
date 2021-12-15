package com.batdaulaptrinh.completlearningenglishapp.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentSignInBinding
import com.batdaulaptrinh.completlearningenglishapp.ui.introduction.IntroductionActivity
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle


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
                MotionToast.createColorToast(
                    context as Activity,
                    "Login fail",
                    "Invalid email format",
                    MotionToastStyle.WARNING,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(context as Activity, R.font.helvetica_regular)
                )
                return@setOnClickListener
            } else if (binding.passwordEdt.text.toString().length < 6) {
                MotionToast.createColorToast(
                    context as Activity,
                    "Login fail",
                    "Password need at least 6 letters",
                    MotionToastStyle.WARNING,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(context as Activity, R.font.helvetica_regular)
                )


                return@setOnClickListener
            } else if (binding.emailEdt.text.toString() == "team6@10diem.com" && binding.passwordEdt.text.toString() == "camonco") {
                Thread.sleep(1000)
                startActivity(Intent(context, IntroductionActivity::class.java))
                activity?.finish()
            } else {
                MotionToast.createColorToast(
                    context as Activity,
                    "Login fail",
                    "Wrong email or password",
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(context as Activity, R.font.helvetica_regular)
                )
            }
        }
        return binding.root
    }

    private fun isValidEmail(target: String?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}