package com.batdaulaptrinh.completlearningenglishapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentWordMoreDetailBinding
import com.batdaulaptrinh.completlearningenglishapp.model.Word

class WordMoreDetailFragment : Fragment() {
    companion object {
        val KEY_MORE_DETAIL = "KEY_MORE_DETAIL"
    }

    lateinit var binding: FragmentWordMoreDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_word_more_detail, container, false)
        // Inflate the layout for this fragment
        binding.previewImg.visibility = View.GONE
        arguments?.let { bundle ->
            val word = bundle.get(KEY_MORE_DETAIL)
            if (word is Word) {
                binding.webView.loadUrl(word.clean_word_url)
            }
        }
        return binding.root
    }

}