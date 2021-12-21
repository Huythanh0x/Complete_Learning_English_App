package com.batdaulaptrinh.completlearningenglishapp.ui.login

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
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
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentSignUpBinding
import com.batdaulaptrinh.completlearningenglishapp.databinding.SignUpSuccessfullyDialogBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.util.*

class SignUpFragment : Fragment() {
    private val CHOOSEIMAGECODE = 11123
    var selectedPhotoUri: Uri? = null
    lateinit var binding: FragmentSignUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        binding.backwardImg.setOnClickListener { findNavController().popBackStack() }
        binding.signUpTxt.setOnClickListener {
            if (selectedPhotoUri == null) {
                MotionToast.createColorToast(
                    context as Activity,
                    "Sign up failed",
                    "Please choose a avatar image",
                    MotionToastStyle.WARNING,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(context as Activity, R.font.helvetica_regular)
                )
                return@setOnClickListener
            } else if (!isValidEmail(binding.emailEdt.text.toString())) {
                MotionToast.createColorToast(
                    context as Activity,
                    "Sign up fail",
                    "Invalid email format",
                    MotionToastStyle.WARNING,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(context as Activity, R.font.helvetica_regular)
                )
                return@setOnClickListener
            } else if (binding.nameEdt.text.toString().isEmpty()) {
                MotionToast.createColorToast(
                    context as Activity,
                    "Sign up failed",
                    "Name can't be empty",
                    MotionToastStyle.WARNING,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(context as Activity, R.font.helvetica_regular)
                )
                return@setOnClickListener
            } else if (binding.passwordEdt.text.toString().length < 6) {
                MotionToast.createColorToast(
                    context as Activity,
                    "Sign up failed",
                    "Password need at least 6 chars",
                    MotionToastStyle.WARNING,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(context as Activity, R.font.helvetica_regular)
                )
                return@setOnClickListener
            } else if (binding.passwordEdt.text.toString() != binding.confirmPasswordEdt.text.toString()) {
                MotionToast.createColorToast(
                    context as Activity,
                    "Sign up failed",
                    "Confirm password is not match",
                    MotionToastStyle.WARNING,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(context as Activity, R.font.helvetica_regular)
                )
                return@setOnClickListener
            }
            performRegister()

        }
        binding.avatarCv.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, CHOOSEIMAGECODE)
        }
        return binding.root
    }

    private fun isValidEmail(target: String?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            data?.let {
                binding.avatarImg.setImageURI(data.data)
                selectedPhotoUri = data.data
            }
        }

    }

    private fun performRegister() {
        binding.progressContainerCl.visibility = View.VISIBLE
        binding.mainContainerCl.alpha = 0.3f
        val email = binding.emailEdt.text.toString()
        val password = binding.passwordEdt.text.toString()
        if (email.isEmpty() || password.isEmpty()) {
            MotionToast.createColorToast(
                context as Activity,
                "Sign up failed",
                "Please enter your email and password",
                MotionToastStyle.ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(context as Activity, R.font.helvetica_regular)
            )
        } else {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (!it.isSuccessful) {
                        return@addOnCompleteListener
                    }
                    uploadImageToFirebase()
                    Log.d("TAG LOAD IMAGE", "successfully")
                    return@addOnCompleteListener
                }
                .addOnFailureListener {
                    Log.e("CREATE ACCOUNT TAG", "$it")
                    binding.progressContainerCl.visibility = View.GONE
                    binding.mainContainerCl.alpha = 1f
                    binding.root.alpha = 1f
                    MotionToast.createColorToast(
                        context as Activity,
                        "Sign up fail",
                        it.message.toString(),
                        MotionToastStyle.ERROR,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(context as Activity, R.font.helvetica_regular)
                    )
                }
        }
    }

    private fun createSignUpSuccessfullyDialog() {
        val dialogBinding = DataBindingUtil.inflate<SignUpSuccessfullyDialogBinding>(
            layoutInflater,
            R.layout.sign_up_successfully_dialog,
            null,
            false
        )

        val dialog = AlertDialog.Builder(context).setView(dialogBinding.root).create()
        dialog.setCancelable(false)
        dialogBinding.signInBtn.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
            //TODO("navigate to sign in BUG")
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun uploadImageToFirebase() {

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/image/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                binding.progressContainerCl.visibility = View.GONE
                binding.mainContainerCl.alpha = 1f
                try {
                    createSignUpSuccessfullyDialog()
                } catch (e: Exception) {
                    Log.e("SIGN UP DIALOG TAG", e.message.toString())
                }
                ref.downloadUrl.addOnSuccessListener {
                    saveUserFirebaseDatabase(it.toString())
                }
            }
            .addOnFailureListener {
                Log.e("IMAGE UPLOAD TAG", it.message.toString())
            }
    }

    private fun saveUserFirebaseDatabase(profileImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref =
            FirebaseDatabase.getInstance("https://cuoikine-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("/users/$uid")
        val user = User(uid, binding.nameEdt.text.toString(), profileImageUrl)

        ref.setValue(user)
    }
}


class User(var uid: String, val username: String, val profilenImageUrl: String)