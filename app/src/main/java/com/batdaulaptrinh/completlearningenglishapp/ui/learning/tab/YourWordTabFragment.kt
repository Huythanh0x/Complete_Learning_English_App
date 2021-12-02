package com.batdaulaptrinh.completlearningenglishapp.ui.learning.tab

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.data.database.LearningAppDatabase
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentYourWordTabBinding
import com.batdaulaptrinh.completlearningenglishapp.databinding.YourWordSortBottomSheetBinding
import com.batdaulaptrinh.completlearningenglishapp.repository.WordRepository
import com.batdaulaptrinh.completlearningenglishapp.ui.adapter.WordListRecyclerAdapter
import com.batdaulaptrinh.completlearningenglishapp.ui.home.WordDetailFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.URL
import java.net.URLConnection


class YourWordTabFragment : Fragment() {
    lateinit var yourWordViewModel: YourWordViewModel
    lateinit var adapter: WordListRecyclerAdapter
    lateinit var binding: FragmentYourWordTabBinding
    private var previousTotal = 0
    private var loading = true
    private val visibleThreshold = 5
    var firstVisibleItem = 0
    var visibleItemCount:Int = 0
    var totalItemCount:Int = 0
    lateinit var layoutManager:LinearLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_your_word_tab, container, false)
        // Inflate the layout for this fragment
        val wordDao = LearningAppDatabase.getInstance(requireContext()).wordDao
        val learnedDateDao = LearningAppDatabase.getInstance(requireContext()).learnedDateDAO
        val wordRepository = WordRepository(wordDao, learnedDateDao)
        val yourWordViewModelFactory = YourWordViewModelFactory(wordRepository)

        yourWordViewModel =
            ViewModelProvider(this, yourWordViewModelFactory)[YourWordViewModel::class.java]
        adapter = WordListRecyclerAdapter(arrayListOf(), { word ->
            playSound(word.mp3_us)
        }, { word ->
                findNavController().navigate(R.id.action_navigation_learning_to_wordDetailFragment,
                bundleOf(WordDetailFragment.DETAIL_WORK_KEY to word))
        }, { word ->
            yourWordViewModel.deleteFavouriteWord(word._id)
            yourWordViewModel.updateListWordWord()
            Snackbar.make(binding.root,
                "${word.en_word} was removed from your wordlist",
                Snackbar.LENGTH_LONG).setAction("Undo") {
                restoreYourWord(word._id)
            }.show()
        })
        yourWordViewModel.listYourWord.observe(viewLifecycleOwner, { listWord ->
            adapter.addList(listWord)
        })

        binding.yourWordsRecyclerView.adapter = adapter
//        TODO("click outside to collapse bottom sheet")
        //      TODO("draggable")
        //    TODO("word suggestion")
        layoutManager = LinearLayoutManager(requireContext())
        binding.yourWordsRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                visibleItemCount = binding.yourWordsRecyclerView.childCount;
                totalItemCount = binding.yourWordsRecyclerView.layoutManager?.itemCount ?: 0;
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                    <= (firstVisibleItem + visibleThreshold)) {
                    // End has been reached

                    Log.i("Yaeye!", "end called");

                    // Do something

                    loading = true;
                }
            }
        })

        binding.sortImg.setOnClickListener() {
            createSortBottomSheet()
        }
        return binding.root
    }

    private fun playSound(mp3Us: String) {
        try{
            GlobalScope.launch(Dispatchers.IO) {
                val base64String = getByteArrayFromImageURL(mp3Us)
                if (base64String != null) {
                    playAudio(base64String)
                }
            }
        }catch (e: Exception){
            Log.e("response",e.toString())
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
            return Base64.encodeToString(bytesOutputStream.toByteArray(), Base64.DEFAULT).filter { !it.isWhitespace() }
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

    private fun restoreYourWord(_id: String) {
        yourWordViewModel.insertFavouriteWord(_id)
        yourWordViewModel.updateListWordWord()
    }


    private fun createSortBottomSheet() {
        val bindingBottomSheet = DataBindingUtil.inflate<YourWordSortBottomSheetBinding>(
            layoutInflater,
            R.layout.your_word_sort_bottom_sheet,
            null,
            false)
        val realBottomSheet = BottomSheetDialog(requireContext())
        realBottomSheet.setContentView(bindingBottomSheet.root)
        realBottomSheet.setCancelable(false)
        bindingBottomSheet.sortByAZTxt.setOnClickListener {
            Toast.makeText(context, "Sort by ascending alphabet", Toast.LENGTH_SHORT).show()
            realBottomSheet.dismiss()
        }
        bindingBottomSheet.sortByZATxt.setOnClickListener {
            Toast.makeText(context, "Sort by descending alphabet", Toast.LENGTH_SHORT).show()
            realBottomSheet.dismiss()
        }
        bindingBottomSheet.sortByTimeAddAscTxt.setOnClickListener {
            Toast.makeText(context, "Sort by ascending add time", Toast.LENGTH_SHORT).show()
            realBottomSheet.dismiss()
        }
        bindingBottomSheet.sortByTimeAddDesTxt.setOnClickListener {
            Toast.makeText(context, "Sort by descending add time", Toast.LENGTH_SHORT).show()
            realBottomSheet.dismiss()
        }
        bindingBottomSheet.collapseBottomSheetImg.setOnClickListener {
            realBottomSheet.dismiss()
        }
        realBottomSheet.show()
    }

    override fun onResume() {
        yourWordViewModel.updateListWordWord()
        super.onResume()
    }
}
