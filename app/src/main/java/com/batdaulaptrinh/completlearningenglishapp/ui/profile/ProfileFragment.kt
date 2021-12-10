package com.batdaulaptrinh.completlearningenglishapp.ui.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Spinner
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.SwitchCompat
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
        profileViewModel.apply {
            phoneNumberLiveData.observe(viewLifecycleOwner) {
                Log.d("NUMBER TAG", it)
                binding.personalInfoExpandableLayout.secondLayout.findViewById<AppCompatEditText>(R.id.number_info_txt)
                    .setText(it)
            }
            fullNameLiveData.observe(viewLifecycleOwner) {
                Log.d("FULL NAME TAG", it)
                binding.personalInfoExpandableLayout.secondLayout.findViewById<AppCompatEditText>(R.id.name_info_txt)
                    .setText(it)
                binding.nameTxt.text = it
            }
            emailLiveData.observe(viewLifecycleOwner) {
                Log.d("EMAIL TAG", it)
                binding.personalInfoExpandableLayout.secondLayout.findViewById<AppCompatEditText>(R.id.email_info_txt)
                    .setText(it)
            }
            isDarkModeLiveData.observe(viewLifecycleOwner) {
                binding.settingsExpandableLayout.secondLayout.findViewById<SwitchCompat>(R.id.dark_mode_sw).isChecked =
                    it
            }
            preferAccentLiveData.observe(viewLifecycleOwner) {
                binding.settingsExpandableLayout.secondLayout.findViewById<Spinner>(R.id.prefer_accent_sp)
                    .setSelection(profileViewModel.getPreferAccentPosition())
            }
            locationLiveData.observe(viewLifecycleOwner) {
                binding.locationTxt.text = it
                profileViewModel.updateCurrentLocation()
            }
            joinedDateLiveData.observe(viewLifecycleOwner) {
                binding.joinedTimeTxt.text = it
            }
            personalGoalLiveData.observe(viewLifecycleOwner){
                binding.settingsExpandableLayout.secondLayout.findViewById<Spinner>(R.id.daily_goal_sp).setSelection(profileViewModel.getPersonalGoalPosition())
            }
        }
        binding.logoutImg.setOnClickListener {
            startActivity(Intent(context, MainLoginActivity::class.java))
            activity?.finish()
        }
        binding.editProfileCv.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, CHOOSEIMAGECODE)
        }
        binding.personalInfoExpandableLayout.parentLayout.setOnClickListener {
            binding.personalInfoExpandableLayout.toggleLayout()
        }
        binding.personalInfoExpandableLayout.setOnExpandListener { isExpanded ->
            if (isExpanded) {
                binding.personalInfoExpandableLayout.parentLayout.background =
                    AppCompatResources.getDrawable(requireContext(), R.drawable.solid_button_bg)
                binding.personalInfoExpandableLayout.secondLayout.visibility = View.INVISIBLE
            } else {
                binding.settingsExpandableLayout.collapse()
                binding.personalInfoExpandableLayout.parentLayout.background =
                    AppCompatResources.getDrawable(requireContext(), R.drawable.solid_top_conner_bg)
                binding.personalInfoExpandableLayout.secondLayout.visibility = View.VISIBLE
            }
        }
        binding.personalInfoExpandableLayout.secondLayout.findViewById<AppCompatImageView>(R.id.edit_name_btn)
            .setOnClickListener {
                startEditText(binding.personalInfoExpandableLayout.secondLayout.findViewById(R.id.name_info_txt))
                finishEditText(binding.personalInfoExpandableLayout.secondLayout.findViewById(R.id.name_info_txt))
            }

        binding.personalInfoExpandableLayout.secondLayout.findViewById<AppCompatImageView>(R.id.edit_email_info_btn)
            .setOnClickListener {
                startEditText(binding.personalInfoExpandableLayout.secondLayout.findViewById(R.id.email_info_txt))
                finishEditText(binding.personalInfoExpandableLayout.secondLayout.findViewById(R.id.email_info_txt))
            }
        binding.personalInfoExpandableLayout.secondLayout.findViewById<AppCompatImageView>(R.id.edit_phone_number_btn)
            .setOnClickListener {
                startEditText(binding.personalInfoExpandableLayout.secondLayout.findViewById(R.id.number_info_txt))
                finishEditText(binding.personalInfoExpandableLayout.secondLayout.findViewById(R.id.number_info_txt))
            }

        binding.settingsExpandableLayout.parentLayout.setOnClickListener {
            binding.settingsExpandableLayout.toggleLayout()
        }

        binding.settingsExpandableLayout.setOnExpandListener { isExpanded ->
            if (isExpanded) {
                binding.settingsExpandableLayout.parentLayout.background =
                    AppCompatResources.getDrawable(requireContext(), R.drawable.solid_button_bg)
                binding.settingsExpandableLayout.secondLayout.visibility = View.INVISIBLE
            } else {
                binding.personalInfoExpandableLayout.collapse()
                binding.settingsExpandableLayout.parentLayout.background =
                    AppCompatResources.getDrawable(requireContext(), R.drawable.solid_top_conner_bg)
                binding.settingsExpandableLayout.secondLayout.visibility = View.VISIBLE
            }
        }

        binding.avatarCv.setOnClickListener {
            val bitmap = binding.avatarImg.drawable.toBitmap()
            findNavController().navigate(R.id.action_navigation_profile_to_showFullSizeAvatar,
                bundleOf(KEY_AVATAR to bitmap))
        }
        binding.settingsExpandableLayout.secondLayout.findViewById<Spinner>(R.id.prefer_accent_sp).onItemSelectedListener =
            preferAccentSpinnerClickListener
        binding.settingsExpandableLayout.secondLayout.findViewById<SwitchCompat>(R.id.dark_mode_sw)
            .setOnCheckedChangeListener { switch, isCheck ->
                profileViewModel.putDarMode(isCheck)
            }
        binding.settingsExpandableLayout.secondLayout.findViewById<Spinner>(R.id.daily_goal_sp).onItemSelectedListener = dailyGoalSpinnerClickListener
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return binding.root
    }

    private fun startEditText(editText: AppCompatEditText) {
        editText.isFocusable = true
        editText.isCursorVisible = true
        editText.isFocusableInTouchMode = true
        editText.requestFocus()
        editText.setSelection((editText.text?.length ?: 0))
        val imm =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun finishEditText(editText: AppCompatEditText) {
        editText.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                editText.isFocusable = false
                editText.isCursorVisible = false
                val imm =
                    activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm!!.hideSoftInputFromWindow(editText.windowToken, 0)
                profileViewModel.putEmail(binding.personalInfoExpandableLayout.secondLayout.findViewById<AppCompatEditText>(
                    R.id.email_info_txt).text.toString())
                profileViewModel.putPhoneNumber(binding.personalInfoExpandableLayout.secondLayout.findViewById<AppCompatEditText>(
                    R.id.number_info_txt).text.toString())
                profileViewModel.putFullName(binding.personalInfoExpandableLayout.secondLayout.findViewById<AppCompatEditText>(
                    R.id.name_info_txt).text.toString())
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

    private val preferAccentSpinnerClickListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
            profileViewModel.putPreferAccent(position)
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
        }
    }

    private val dailyGoalSpinnerClickListener = object: AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
            profileViewModel.putPersonalGoal(position)
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
        }
    }
}