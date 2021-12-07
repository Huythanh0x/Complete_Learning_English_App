package com.batdaulaptrinh.completlearningenglishapp.ui.learning.tab

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.data.database.LearningAppDatabase
import com.batdaulaptrinh.completlearningenglishapp.databinding.AllWordsSortBottomSheetBinding
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentAllWordTabBinding
import com.batdaulaptrinh.completlearningenglishapp.repository.WordRepository
import com.batdaulaptrinh.completlearningenglishapp.ui.adapter.WordListRecyclerAdapter
import com.batdaulaptrinh.completlearningenglishapp.ui.home.WordDetailFragment
import com.batdaulaptrinh.completlearningenglishapp.utils.Utils
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.URL
import java.net.URLConnection

class AllWordTabFragment : Fragment() {
    lateinit var allWordViewModel: AllWordViewModel
    lateinit var adapter: WordListRecyclerAdapter
    lateinit var binding: FragmentAllWordTabBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_all_word_tab, container, false)
        // Inflate the layout for this fragment
        val wordDao = LearningAppDatabase.getInstance(requireContext()).wordDao
        LearningAppDatabase.getInstance(requireContext()).learnedDateDAO
        val wordRepository = WordRepository(wordDao)
        val allWordViewModelFactory = AllWordViewModelFactory(wordRepository)
        allWordViewModel =
            ViewModelProvider(this, allWordViewModelFactory)[AllWordViewModel::class.java]
        adapter = WordListRecyclerAdapter(arrayListOf(), { word ->
            findNavController().navigate(R.id.action_navigation_learning_to_wordDetailFragment,
                bundleOf(WordDetailFragment.DETAIL_WORK_KEY to word))
        }) { word ->
            if (word.is_favourite == 1) {
                allWordViewModel.insertFavouriteWord(word._id)
                Toast.makeText(requireContext(),
                    "${word.en_word} was added to your word list",
                    Toast.LENGTH_LONG).show()
            } else {
                allWordViewModel.deleteFavouriteWord(word._id)
            }
        }
        binding.allWordsRecyclerView.adapter = adapter
        allWordViewModel.allWords.observe(viewLifecycleOwner, { listWord ->
            binding.loadingCl.visibility = View.GONE
            Log.d("LAST ACTION TAG", allWordViewModel.lastAction.value.toString())
            adapter.addList(listWord)
        })
        binding.sortImg.setOnClickListener {
            binding.searchView.setQuery("", false)
            binding.searchView.clearFocus()
            createSortBottomSheet()
        }
        allWordViewModel.lastAction.observe(viewLifecycleOwner, {
            allWordViewModel.allWords.value?.let { listWord -> adapter.addList(listWord) }
        })
        binding.searchView.setOnQueryTextListener(searchListener)
        return binding.root
    }


    @DelicateCoroutinesApi
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


    private fun createSortBottomSheet() {
        val bindingBottomSheet = DataBindingUtil.inflate<AllWordsSortBottomSheetBinding>(
            layoutInflater,
            R.layout.all_words_sort_bottom_sheet,
            null,
            false)
        val realBottomSheet = BottomSheetDialog(requireContext())
        realBottomSheet.setContentView(bindingBottomSheet.root)
        bindingBottomSheet.sortByAZTxt.setOnClickListener {
            allWordViewModel.setActionSort()
            allWordViewModel.getAllWordSortedListAZ()
            binding.allWordsRecyclerView.scrollToPosition(0)
            binding.sortImg.setImageResource(R.drawable.sort_by_alphabet_asc)
            realBottomSheet.dismiss()
        }
        bindingBottomSheet.sortByZATxt.setOnClickListener {
            allWordViewModel.setActionSort()
            allWordViewModel.getAllWordSortedListZA()
            binding.allWordsRecyclerView.scrollToPosition(0)
            binding.sortImg.setImageResource(R.drawable.sort_by_alphabet_desc)
            realBottomSheet.dismiss()
        }
        bindingBottomSheet.sortByLevelDesTxt.setOnClickListener {
            allWordViewModel.setActionSort()
            allWordViewModel.getAllWordSortedListLevelDESC()
            binding.allWordsRecyclerView.scrollToPosition(0)
            binding.sortImg.setImageResource(R.drawable.sort_by_level_desc)
            realBottomSheet.dismiss()
        }
        bindingBottomSheet.sortByLevelAscTxt.setOnClickListener {
            allWordViewModel.setActionSort()
            allWordViewModel.getAllWordSortedListLevelASC()
            binding.allWordsRecyclerView.scrollToPosition(0)
            binding.sortImg.setImageResource(R.drawable.sort_by_level_asc)
            realBottomSheet.dismiss()
        }
        bindingBottomSheet.collapseBottomSheetImg.setOnClickListener {
            realBottomSheet.dismiss()
        }
        realBottomSheet.show()
    }

    override fun onResume() {
        allWordViewModel.updateListWord()
        super.onResume()
    }

    private val searchListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let {
                allWordViewModel.setActionSearch()
                allWordViewModel.getSearchAllWord(it)
            }
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            if (newText != null && newText.isNotEmpty()) {
                allWordViewModel.setActionSearch()
                allWordViewModel.getSearchAllWord(newText)
            }
            if (newText != null && newText.isEmpty()) {
                if (allWordViewModel.lastAction.value == Utils.SEARCH) {
                    allWordViewModel.getSearchAllWord(newText)
                }
            }
            return false
        }
    }
}