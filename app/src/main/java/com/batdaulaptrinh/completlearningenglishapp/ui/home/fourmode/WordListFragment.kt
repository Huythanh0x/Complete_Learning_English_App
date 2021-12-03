package com.batdaulaptrinh.completlearningenglishapp.ui.home.fourmode

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.data.database.LearningAppDatabase
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentWordListBinding
import com.batdaulaptrinh.completlearningenglishapp.model.WordSet
import com.batdaulaptrinh.completlearningenglishapp.repository.WordRepository
import com.batdaulaptrinh.completlearningenglishapp.ui.adapter.SetWordListRecyclerAdapter
import com.batdaulaptrinh.completlearningenglishapp.ui.home.ChoosingModeFragment
import com.batdaulaptrinh.completlearningenglishapp.ui.home.WordDetailFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.URL
import java.net.URLConnection


class WordListFragment : Fragment() {
    lateinit var binding: FragmentWordListBinding
    lateinit var wordListViewModel: WordListViewModel
    lateinit var adapter: SetWordListRecyclerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            com.batdaulaptrinh.completlearningenglishapp.R.layout.fragment_word_list,
            container,
            false)
        val wordDao = LearningAppDatabase.getInstance(requireContext()).wordDao
        LearningAppDatabase.getInstance(requireContext()).learnedDateDAO
        val wordRepository = WordRepository(wordDao)
        val wordListViewModelFactory = WordListViewModelFactory(wordRepository)
        wordListViewModel =
            ViewModelProvider(this, wordListViewModelFactory)[WordListViewModel::class.java]

        adapter = SetWordListRecyclerAdapter(arrayListOf(), { word ->
            findNavController().navigate(R.id.action_wordListFragment_to_wordDetailFragment,
                bundleOf(WordDetailFragment.DETAIL_WORK_KEY to word))
        }) { word ->
            if (word.is_favourite == 1) {
                wordListViewModel.insertFavouriteWord(word._id)
                Toast.makeText(requireContext(),
                    "Add ${word.en_word} into your wordlist",
                    Toast.LENGTH_SHORT).show()
            } else {
                wordListViewModel.deleteFavouriteWord(word._id)
            }

        }
        binding.recyclerView.adapter = adapter

        arguments?.let {
            val setWord = it.get(ChoosingModeFragment.KEY_ARGS_SET)
            if (setWord is WordSet) {
                wordListViewModel.getNewWordList(setWord.setNth)
            }
        }
        wordListViewModel.listWord.observe(viewLifecycleOwner, { listWord ->
            adapter.addList(listWord)
        })

        //TODO FAKING

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
            var read: Int
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
        } catch (ex: Exception) {
            print(ex.message)
        }
    }

}