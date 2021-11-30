package com.batdaulaptrinh.completlearningenglishapp.ui.login

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentSignUpBinding
import com.batdaulaptrinh.completlearningenglishapp.databinding.SignUpSuccessfullyDialogBinding

class SignUpFragment : Fragment() {
    private val CHOOSEIMAGECODE = 11123
    lateinit var binding: FragmentSignUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        binding.backwardImg.setOnClickListener { findNavController().popBackStack() }
        binding.signUpTxt.setOnClickListener {
            if (!isValidEmail(binding.emailEdt.text.toString())) {
                Toast.makeText(context, "Invalid email format", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (binding.nameEdt.text.toString().isEmpty()) {
                Toast.makeText(context, "Name can't be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (binding.passwordEdt.text.toString().length < 6) {
                Toast.makeText(context,
                    "Need at least 6 characters for password",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (binding.passwordEdt.text.toString() != binding.confirmPasswordEdt.text.toString()) {
                Toast.makeText(context, "confirm password is not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            createSignUpSuccessfullyDialog()
        }
        binding.avatarCv.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, CHOOSEIMAGECODE)
        }
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
            //TODO("cancelable = false, navigate to sign in BUG")
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun isValidEmail(target: String?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            data?.let {
                binding.avatarImg.setImageURI(data.data)
            }
        }
    }
}