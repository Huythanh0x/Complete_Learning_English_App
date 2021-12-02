package com.batdaulaptrinh.completlearningenglishapp.ui.home

import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.data.database.LearningAppDatabase
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentWordDetailBinding
import com.batdaulaptrinh.completlearningenglishapp.model.MinimalWord
import com.batdaulaptrinh.completlearningenglishapp.repository.WordRepository
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.URL
import java.net.URLConnection

class WordDetailFragment : Fragment() {
    lateinit var wordDetailViewModel: WordDetailViewModel

    companion object {
        val DETAIL_WORK_KEY = "DETAIL_WORD_KEY"
    }

    lateinit var binding: FragmentWordDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_word_detail, container, false)
        // Inflate the layout for this fragment
        val wordDao = LearningAppDatabase.getInstance(requireContext()).wordDao
        val learnedDateDao = LearningAppDatabase.getInstance(requireContext()).learnedDateDAO
        val wordRepository = WordRepository(wordDao, learnedDateDao)
        val wordDetailViewModelFactory = WordDetailViewModelFactory(wordRepository)
        wordDetailViewModel =
            ViewModelProvider(this, wordDetailViewModelFactory)[WordDetailViewModel::class.java]

        arguments?.let { bundle ->
            val minimalWord = bundle.get(DETAIL_WORK_KEY)
            if (minimalWord is MinimalWord) {
                wordDetailViewModel.getWord(minimalWord._id)

            }
        }
        binding.moreDetailTxt.setOnClickListener {
            findNavController().navigate(R.id.action_wordDetailFragment_to_wordMoreDetail,
                bundleOf(WordMoreDetailFragment.KEY_MORE_DETAIL to wordDetailViewModel.fullWord.value))
        }
        wordDetailViewModel.fullWord.observe(viewLifecycleOwner, { fullWord ->
            binding.descriptionContentTxt.text = fullWord.definition
            binding.ukApiTxt.text = fullWord.api_uk
            binding.usApiTxt.text = fullWord.api_us
            binding.wordTypeTxt.text = fullWord.type
            binding.exampleContentTxt.text = fullWord.examples.replace(";", "\n")
            binding.enWordTxt.text = fullWord.en_word
            binding.ukSpeakerImg.setOnClickListener {
                playSound(fullWord.mp3_uk)
            }
            binding.usSpeakerImg.setOnClickListener {
                playSound(fullWord.mp3_us)
            }
            var bitmap = BitmapFactory.decodeResource(resources, R.drawable.app_logo_img)
            try {
                val decodedString: ByteArray = Base64.decode(fullWord.thumbnail, Base64.DEFAULT)
                bitmap =
                    BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            } catch (e: Exception) {
                Log.e("LOAD IMAGE", e.toString())
            }
            Glide.with(this)
                .load(bitmap) // image url
                .placeholder(R.drawable.app_logo_img) // any placeholder to load at start
                .error(R.drawable.app_logo_img)  // any image in case of error
                .centerCrop()
                .into(binding.thumbnailExampleImg);  // imageview object

        })
        binding.backwardImg.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root

    }

    private fun playSound(mp3Us: String) {
        try {
            GlobalScope.launch(Dispatchers.IO) {
                val base64String = getByteArrayFromImageURL(mp3Us)
                if (base64String != null) {
                    playAudio(base64String)
                }
            }
        } catch (e: Exception) {
            Log.e("response", e.toString())
        }
    }

    private fun getByteArrayFromImageURL(url: String): String? {
        try {
            val imageUrl = URL(url)
            val urlConnection: URLConnection = imageUrl.openConnection()
            val inputStream: InputStream = urlConnection.getInputStream()
            val bytesOutputStream = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var read = 0
            while (inputStream.read(buffer, 0, buffer.size).also { read = it } != -1) {
                bytesOutputStream.write(buffer, 0, read)
            }
            bytesOutputStream.flush()
            return Base64.encodeToString(bytesOutputStream.toByteArray(), Base64.DEFAULT)
                .filter { !it.isWhitespace() }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return null
    }

    private fun playAudio(base64EncodedString: String) {
        try {
            val url = "data:audio/mp3;base64,$base64EncodedString"
            val mediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(url)
            mediaPlayer.prepare()
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener {
                mediaPlayer->
                mediaPlayer.release()
            }
        } catch (ex: Exception) {
            print(ex.message)
        }
    }


}