package com.batdaulaptrinh.completlearningenglishapp.ui.profile

import android.app.Activity
import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.graphics.drawable.toBitmap
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.work.*
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.data.sharedPreferences.SharePreferencesProvider
import com.batdaulaptrinh.completlearningenglishapp.databinding.ChangePasswordDialogBinding
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentProfileBinding
import com.batdaulaptrinh.completlearningenglishapp.databinding.LogoutDialogBinding
import com.batdaulaptrinh.completlearningenglishapp.notification.NotifyLearningWordWorker
import com.batdaulaptrinh.completlearningenglishapp.notification.ReminderWorker
import com.batdaulaptrinh.completlearningenglishapp.ui.login.MainLoginActivity
import com.batdaulaptrinh.completlearningenglishapp.utils.Utils
import com.google.firebase.auth.FirebaseAuth
import org.threeten.bp.DateTimeUtils
import timber.log.Timber
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit
import java.util.concurrent.TimeUnit


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
            Log.d("tesst", isCheck.toString())
            if (isCheck) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
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
            preferLearningTimeLiveData.observe(viewLifecycleOwner) {
                binding.preferLearningTimeTxt.text = it
            }
            loopNotificationLiveData.observe(viewLifecycleOwner) {
                binding.loopNotificationSp.setSelection(
                    when (it) {
                        15 -> 0
                        30 -> 1
                        45 -> 2
                        60 -> 3
                        90 -> 4
                        120 -> 5
                        else -> 3
                    }
                )
                Timber.d("$it")
            }
        }


        binding.logoutImg.setOnClickListener {
            createLogoutDialog()
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
            val animation = AnimationUtils.loadAnimation(context, R.anim.press_view_alpla)
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(p0: Animation?) {}

                override fun onAnimationEnd(p0: Animation?) {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    startActivityForResult(intent, CHOOSEIMAGECODE)
                }

                override fun onAnimationRepeat(p0: Animation?) {}
            })
            it.startAnimation(animation)
        }

        binding.avatarCv.setOnClickListener {
            val bitmap = binding.avatarImg.drawable.toBitmap()
            val animation = AnimationUtils.loadAnimation(context, R.anim.press_view_alpla)
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(p0: Animation?) {}

                override fun onAnimationEnd(p0: Animation?) {
                    findNavController().navigate(
                        R.id.action_navigation_profile_to_showFullSizeAvatar,
                        bundleOf(KEY_AVATAR to bitmap)
                    )
                }

                override fun onAnimationRepeat(p0: Animation?) {}
            })
            it.startAnimation(animation)
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
        binding.changePasswordTxt.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(context, R.anim.press_view_alpla)
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(p0: Animation?) {
//                    TODO("Not yet implemented")
                }

                override fun onAnimationEnd(p0: Animation?) {
                    createChangePasswordDialog()
//                    TODO("Not yet implemented")
                }

                override fun onAnimationRepeat(p0: Animation?) {
//                    TODO("Not yet implemented")
                }
            })
            it.startAnimation(animation)
            createChangePasswordDialog()
        }
        binding.preferLearningTimeTxt.setOnClickListener {
            val timePickerDialogListener =
                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    profileViewModel.putPreferLearningTime("$hourOfDay:$minute")
                    updateReminderNotification()
                }
            val olderPreferTime = SharePreferencesProvider(requireContext()).getPreferLearningTime()

            TimePickerDialog(
                context,
                timePickerDialogListener,
                olderPreferTime.split(":")[0].toInt(),
                olderPreferTime.split(":")[1].toInt(),
                true
            ).show()
        }
        binding.loopNotificationSp.onItemSelectedListener = loopNotificationSpinnerClickListener
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
        val rotate = RotateAnimation(
            fromDegree,
            toDegree,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
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

    fun createChangePasswordDialog() {

        val dialogBinding = DataBindingUtil.inflate<ChangePasswordDialogBinding>(
            LayoutInflater.from(context),
            R.layout.change_password_dialog,
            null,
            false
        )
        val dialog = AlertDialog.Builder(context).setView(dialogBinding.root).create()
        dialogBinding.saveBtn.setOnClickListener {
            if (dialogBinding.oldPasswordEdt.text.toString()
                    .isEmpty() || dialogBinding.confirmNewPasswordEdt.text.toString()
                    .isEmpty() || dialogBinding.newPasswordEdt.text.toString().isEmpty()
            ) {
                return@setOnClickListener
            }
            //TODO implement change password here
        }
        dialogBinding.discardBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun createLogoutDialog() {
        val dialogBinding = DataBindingUtil.inflate<LogoutDialogBinding>(
            LayoutInflater.from(context),
            R.layout.logout_dialog,
            null,
            false
        )
        val dialog = AlertDialog.Builder(context).setView(dialogBinding.root).create()
        dialogBinding.answerYesBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(context, MainLoginActivity::class.java))
            dialog.dismiss()
            activity?.finish()
        }
        dialogBinding.answerNoBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun updateNotificationLearningWord() {
        val minuteTimeCycle =
            SharePreferencesProvider(requireContext()).getLoopNotification().toLong()
        val notificationWork =
            PeriodicWorkRequestBuilder<NotifyLearningWordWorker>(minuteTimeCycle, TimeUnit.MINUTES)
                .setInitialDelay(Duration.ofMinutes(minuteTimeCycle))
                .build()
        WorkManager.getInstance(requireContext())
            .enqueueUniquePeriodicWork(
                Utils.UNIQUE_NOTIFY_LEARNING_WORD_WORKER, ExistingPeriodicWorkPolicy.REPLACE,
                notificationWork
            )
    }
    private fun updateReminderNotification() {
        val preferLearningTime =
            SharePreferencesProvider(requireContext()).getPreferLearningTime()
        val alarmTime = LocalTime.of(
            preferLearningTime.split(":")[0].toInt(),
            preferLearningTime.split(":")[1].toInt()
        )
        var now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        val nowTime = now.toLocalTime()
        if (nowTime == alarmTime || nowTime.isAfter(alarmTime)) {
            now = now.plusDays(1)
        }
        now = now.withHour(alarmTime.hour)
            .withMinute(alarmTime.minute) // .withSecond(alarmTime.second).withNano(alarmTime.nano)
        val delay = Duration.between(LocalDateTime.now(), now)

        val notificationWork = OneTimeWorkRequest.Builder(ReminderWorker::class.java)
            .setInitialDelay(delay).build()

        WorkManager.getInstance(requireContext()).beginUniqueWork(
            Utils.UNIQUE_REMINDER_WORKER,
            ExistingWorkPolicy.REPLACE,
            notificationWork
        ).enqueue()
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
    private val loopNotificationSpinnerClickListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
            if(profileViewModel.isTheSameAsStoredPosition(position)) return
            profileViewModel.putLoopNotification(position)
            updateNotificationLearningWord()
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {}
    }
}