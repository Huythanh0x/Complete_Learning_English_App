package com.batdaulaptrinh.completlearningenglishapp.ui.profile

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentShowFullSizeAvatarBinding

class ShowFullSizeAvatar : Fragment() {
    lateinit var binding: FragmentShowFullSizeAvatarBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_show_full_size_avatar,
            container,
            false)
        arguments?.let {
            val bitmap = it.get(ProfileFragment.KEY_AVATAR)
            if (bitmap is Bitmap){
                binding.fullSizeImg.setImageBitmap(bitmap)
            }
        }
        binding.backwardImg.setOnClickListener{
            findNavController().popBackStack()
        }
        return binding.root
    }
}