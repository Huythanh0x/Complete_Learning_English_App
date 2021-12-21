package com.batdaulaptrinh.completlearningenglishapp.ui.login

import android.app.Activity
import android.app.AlertDialog
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
import com.batdaulaptrinh.completlearningenglishapp.databinding.DialogForgotPasswordBinding
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentSignInBinding
import com.batdaulaptrinh.completlearningenglishapp.ui.introduction.IntroductionActivity
import com.google.firebase.auth.FirebaseAuth
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle


/////// new

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
            } else {
                binding.progressContainerCl.visibility = View.VISIBLE
                binding.mainContainerCl.alpha = 0.3f
                val email = binding.emailEdt.text.toString()
                val pass = binding.passwordEdt.text.toString()
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener {
                        if (!it.isSuccessful) return@addOnCompleteListener
                        MotionToast.createColorToast(
                            context as Activity,
                            "Login Successfully",
                            "You had successfully Signed in",
                            MotionToastStyle.SUCCESS,
                            MotionToast.GRAVITY_TOP,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(context as Activity, R.font.helvetica_regular)
                        )

                        startActivity(Intent(context, IntroductionActivity::class.java))
                        activity?.finish()

                    }
                    .addOnFailureListener {
                        binding.progressContainerCl.visibility = View.GONE
                        binding.mainContainerCl.alpha = 1f
                        MotionToast.createColorToast(
                            context as Activity,
                            "Sign in fail",
                            "${it.message}",
                            MotionToastStyle.WARNING,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(context as Activity, R.font.helvetica_regular)
                        )
                        Log.e("LOGIN TAG", "${it.message}")
                    }
            }
        }

        binding.forgotPasswordTxt.setOnClickListener {
            createForgotPasswordDialog()
        }
        return binding.root
    }

    private fun createForgotPasswordDialog() {
        val dialogBinding = DataBindingUtil.inflate<DialogForgotPasswordBinding>(
            LayoutInflater.from(requireContext()),
            R.layout.dialog_forgot_password,
            null,
            false
        )
        val dialog =
            AlertDialog.Builder(requireContext()).setView(dialogBinding.root)
                .create()

        dialogBinding.buttonBack.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.btnReset.setOnClickListener {
            dialogBinding.edtEmail.editText?.text?.let {
                forgotPassword(dialog, dialogBinding)
            }
        }
        dialog.show()
    }

    private fun forgotPassword(dialog: AlertDialog, dialogBinding: DialogForgotPasswordBinding) {
        val email = dialogBinding.emailEdt.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            MotionToast.createColorToast(
                context as Activity,
                "Reset password failed",
                "Invalid email format",
                MotionToastStyle.WARNING,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(context as Activity, R.font.helvetica_regular)
            )
            return
        }
        dialogBinding.progressContainerCl.visibility = View.VISIBLE
        dialogBinding.dialogRoot.alpha = 0.3f
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
                MotionToast.createColorToast(
                    context as Activity,
                    "Reset password successfully",
                    "Check your email for resetting",
                    MotionToastStyle.SUCCESS,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(context as Activity, R.font.helvetica_regular)
                )
                dialogBinding.progressContainerCl.visibility = View.GONE
                dialogBinding.dialogRoot.alpha = 1f
                dialog.dismiss()
            }
            .addOnFailureListener {
                MotionToast.createColorToast(
                    context as Activity,
                    "Reset password failed",
                    it.message.toString(),
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(context as Activity, R.font.helvetica_regular)
                )
                dialogBinding.progressContainerCl.visibility = View.GONE
                dialogBinding.dialogRoot.alpha = 1f
                return@addOnFailureListener
            }
    }

    private fun isValidEmail(target: String?): Boolean {
        return !(TextUtils.isEmpty(target) || !Patterns.EMAIL_ADDRESS.matcher(target.toString()).matches())
    }
}