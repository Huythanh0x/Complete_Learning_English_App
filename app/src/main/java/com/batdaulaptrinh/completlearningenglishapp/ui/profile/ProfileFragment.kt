package com.batdaulaptrinh.completlearningenglishapp.ui.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ImageView
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
        binding.preferAccentSp.onItemSelectedListener = preferAccentSpinnerClickListener
        binding.dailyGoalSp.onItemSelectedListener = personalGoalSpinnerClickListener
        binding.darkModeSw.setOnCheckedChangeListener { switch, isCheck ->
            profileViewModel.putDarMode(isCheck)
        }
        profileViewModel.apply {
            phoneNumberLiveData.observe(viewLifecycleOwner) {
                binding.numberInfoTxt.setText(it)
            }
            fullNameLiveData.observe(viewLifecycleOwner) {
                binding.nameInfoTxt.setText(it)
                binding.nameTxt.text = it
            }
            emailLiveData.observe(viewLifecycleOwner) {
                binding.emailInfoTxt.setText(it)
            }
            isDarkModeLiveData.observe(viewLifecycleOwner) {
                binding.darkModeSw.isChecked = it
            }
            preferAccentLiveData.observe(viewLifecycleOwner) {
                binding.preferAccentSp.setSelection(profileViewModel.getPreferAccentPosition())
            }
            locationLiveData.observe(viewLifecycleOwner) {
                binding.locationTxt.text = it
                profileViewModel.updateCurrentLocation()
            }
            joinedDateLiveData.observe(viewLifecycleOwner) {
                binding.joinedTimeTxt.text = it
            }
            personalGoalLiveData.observe(viewLifecycleOwner) {
                binding.dailyGoalSp.setSelection(profileViewModel.getPersonalGoalPosition())
            }

            isSettingsExpanded.observe(viewLifecycleOwner) {
                rotateImage(binding.settingsArrowImg, !it)
                if (it) {
                    binding.settingsExpandableLayout.expand()
                } else {
                    binding.settingsExpandableLayout.collapse()
                }
            }

            isPersonalExpanded.observe(viewLifecycleOwner) {
                rotateImage(binding.personalInfoArrowImg, !it)
                if (it) {
                    binding.personalInfoExpandableLayout.expand()
                } else {
                    binding.personalInfoExpandableLayout.collapse()
                }
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
        binding.personalInfoArrowImg.setOnClickListener {
            if (binding.personalInfoExpandableLayout.isExpanded) {
                profileViewModel.collapsePersonalInfo()
            } else {
                profileViewModel.expandPersonalInfo()
            }
        }

        binding.settingsArrowImg.setOnClickListener {
            if (binding.settingsExpandableLayout.isExpanded) {
                profileViewModel.collapseSetting()
            } else {
                profileViewModel.expandSetting()
            }
        }

        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
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

    private fun rotateImage(imageView: ImageView, isExpanded: Boolean) {
        var fromDegree = 0f
        var toDegree = 180f
        if (isExpanded) {
            fromDegree = 180f
            toDegree = 0f
        }
        val rotate = RotateAnimation(fromDegree,
            toDegree,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f)
        rotate.duration = 300
        rotate.fillAfter = true
        rotate.interpolator = LinearInterpolator()
        imageView.startAnimation(rotate)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            data?.let {
                binding.avatarImg.setImageURI(data.data)
            }
        }
    }

    private val preferAccentSpinnerClickListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
            profileViewModel.putPreferAccent(position)
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
        }
    }

    private val personalGoalSpinnerClickListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
            profileViewModel.putPersonalGoal(position)
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
        }
    }
}