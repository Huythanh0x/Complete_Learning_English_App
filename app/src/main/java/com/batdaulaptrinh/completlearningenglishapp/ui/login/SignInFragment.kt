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
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.CompleteFlashCardDialogBindingImpl
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
                val email = binding.emailEdt.text.toString()
                val pass = binding.passwordEdt.text.toString()
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener {
                        if (!it.isSuccessful) return@addOnCompleteListener
                        Toast.makeText(activity, "successful", Toast.LENGTH_LONG).show()
                        startActivity(Intent(context, IntroductionActivity::class.java))
                        activity?.finish()

                    }
                    .addOnFailureListener {
                        Toast.makeText(activity, "Please try again", Toast.LENGTH_LONG).show()
                    }
            }
        }

        binding.forgotPasswordTxt.setOnClickListener {
//            val builder = AlertDialog.Builder(requireContext())
//            builder.setTitle("Forgot password")
//            val view = layoutInflater.inflate(R.layout.dialog_forgotpass, null)
//            val username = view.findViewById<EditText>(R.id.et_username)
//            builder.setView(view)
//
//            builder.setPositiveButton("Reset", DialogInterface.OnClickListener {_,_ ->
//                forgotpassword(username)
//            })
//            builder.setNegativeButton("Back", DialogInterface.OnClickListener {_,_ ->
//
//            })
//            builder.show()


            val dialogBinding = DataBindingUtil.inflate<DialogForgotPasswordBinding>(
                LayoutInflater.from(requireContext()),
                R.layout.dialog_forgot_password,
                null,
                false
            )
            val dialog = android.app.AlertDialog.Builder(requireContext()).setView(dialogBinding.root).create()

            dialogBinding.buttonBack.setOnClickListener {
                dialog.dismiss()
            }

            dialogBinding.btnReset.setOnClickListener {
                val email = dialogBinding.edtEmail.editText?.text.toString()
                forgotpassword(email, dialogBinding)
            }

            dialog.show()

        }


        return binding.root
    }

    private fun forgotpassword(email: String, dialogbinding: DialogForgotPasswordBinding ) {
        if (email.isEmpty()) {
            return
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(activity, "email don't invalid", Toast.LENGTH_SHORT).show()
            dialogbinding.edtEmail.editText?.text?.clear()

        } else {

            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener {
                    if (!it.isSuccessful) return@addOnCompleteListener

                    Toast.makeText(activity, "Email send", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener() {
                    Toast.makeText(activity, "Email doesn't exits", Toast.LENGTH_SHORT).show()
                    return@addOnFailureListener
                }
        }


    }

    private fun isValidEmail(target: String?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}