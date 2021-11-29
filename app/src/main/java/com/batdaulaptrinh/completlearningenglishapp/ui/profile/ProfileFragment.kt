package com.batdaulaptrinh.completlearningenglishapp.ui.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentProfileBinding
import com.batdaulaptrinh.completlearningenglishapp.ui.login.MainLoginActivity

class ProfileFragment : Fragment() {
    private val CHOOSEIMAGECODE = 11123
    lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.logoutImg.setOnClickListener {
            startActivity(Intent(context, MainLoginActivity::class.java))
            activity?.finish()
        }
        binding.editNameBtn.setOnClickListener {
            startEditText(binding.nameInfoTxt)
            finishEditText(binding.nameInfoTxt)
        }
        binding.editPhoneNumberBtn.setOnClickListener{
            startEditText(binding.numberInfoTxt)
            finishEditText(binding.numberInfoTxt)
        }
        binding.avatarCv.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, CHOOSEIMAGECODE)
        }

        return binding.root

    }

    private fun startEditText(editText: androidx.appcompat.widget.AppCompatEditText) {
        editText.isFocusable = true
        editText.isFocusableInTouchMode = true
        editText.requestFocus()
        editText.setSelection((editText.text?.length ?: 0))
        val imm =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun finishEditText(editText: androidx.appcompat.widget.AppCompatEditText) {
        editText.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && (event.action == KeyEvent.ACTION_DOWN)) {
                editText.isFocusable = false
                val imm =
                    activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm!!.hideSoftInputFromWindow(editText.windowToken, 0)
                return@OnKeyListener true
            }
            false
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            data?.let {
                binding.avatarImg.setImageURI(data.data)
            }
        }
    }

}