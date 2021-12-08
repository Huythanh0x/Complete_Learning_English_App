package com.batdaulaptrinh.completlearningenglishapp.ui.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentProfileBinding
import com.batdaulaptrinh.completlearningenglishapp.ui.login.MainLoginActivity

class ProfileFragment : Fragment() {
    companion object {
        val KEY_AVATAR = "KEY_AVATAR"
    }

    private val CHOOSEIMAGECODE = 11123
    lateinit var binding: FragmentProfileBinding
    lateinit var profileViewModel: ProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        val viewModelFactory = ProfileViewModelFactory(requireActivity().application)
        profileViewModel = ViewModelProvider(this, viewModelFactory)[ProfileViewModel::class.java]
        binding.preferAccentSp.onItemSelectedListener = spinnerClickListener
        binding.darkModeSw.setOnCheckedChangeListener { switch, isCheck ->
            profileViewModel.putDarMode(isCheck)
        }
        profileViewModel.apply {
            phoneNumberLiveData.observe(viewLifecycleOwner) {
                binding.numberInfoTxt.setText(it)
                Log.d("TAG PROFILE",it.toString())
            }
            fullNameLiveData.observe(viewLifecycleOwner) {
                binding.nameInfoTxt.setText(it)
                binding.nameTxt.text = it
                Log.d("TAG PROFILE",it.toString())
            }
            emailLiveData.observe(viewLifecycleOwner) {
                binding.emailInfoTxt.setText(it)
                Log.d("TAG PROFILE",it.toString())
            }
        }
        binding.logoutImg.setOnClickListener {
            startActivity(Intent(context, MainLoginActivity::class.java))
            activity?.finish()
        }
        binding.editNameBtn.setOnClickListener {
            startEditText(binding.nameInfoTxt)
            finishEditText(binding.nameInfoTxt)
        }
        binding.editPhoneNumberBtn.setOnClickListener {
            startEditText(binding.numberInfoTxt)
            finishEditText(binding.numberInfoTxt)
        }
        binding.editEmailInfoBtn.setOnClickListener {
            startEditText(binding.emailInfoTxt)
            finishEditText(binding.emailInfoTxt)
        }
        binding.editProfileCv.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, CHOOSEIMAGECODE)
        }

        binding.avatarCv.setOnClickListener {
            val bitmap = binding.avatarImg.drawable.toBitmap()
            findNavController().navigate(R.id.action_navigation_profile_to_showFullSizeAvatar,
                bundleOf(KEY_AVATAR to bitmap))
        }
        return binding.root
    }

    private fun startEditText(editText: androidx.appcompat.widget.AppCompatEditText) {
        editText.isFocusable = true
        editText.isCursorVisible = true
        editText.isFocusableInTouchMode = true
        editText.requestFocus()
        editText.setSelection((editText.text?.length ?: 0))
        val imm =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun finishEditText(editText: androidx.appcompat.widget.AppCompatEditText) {
        editText.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                editText.isFocusable = false
                editText.isCursorVisible = false
                val imm =
                    activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm!!.hideSoftInputFromWindow(editText.windowToken, 0)

                profileViewModel.putEmail(binding.emailInfoTxt.text.toString())
                profileViewModel.putPhoneNumber(binding.numberInfoTxt.text.toString())
                profileViewModel.putFullName(binding.nameInfoTxt.text.toString())
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

    private val spinnerClickListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
            profileViewModel.putPreferAccent(position)
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
//            TODO("Not yet implemented")
        }
    }
}