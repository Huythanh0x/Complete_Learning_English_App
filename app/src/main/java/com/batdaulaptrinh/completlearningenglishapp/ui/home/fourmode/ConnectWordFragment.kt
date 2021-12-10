package com.batdaulaptrinh.completlearningenglishapp.ui.home.fourmode

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentConnectWordBinding
import com.batdaulaptrinh.completlearningenglishapp.model.WordSet
import com.batdaulaptrinh.completlearningenglishapp.ui.adapter.EmptyLetterRecyclerAdapter
import com.batdaulaptrinh.completlearningenglishapp.ui.adapter.MissingLetterRecyclerAdapter
import com.batdaulaptrinh.completlearningenglishapp.ui.home.ChoosingModeFragment
import java.util.*

class ConnectWordFragment : Fragment() {
    lateinit var binding: FragmentConnectWordBinding
    private val listEmptyLetter = arrayListOf<String>()
    private val primaryListMissingLetter = arrayListOf<String>()
    private val secondaryListMissingLetter = arrayListOf<String>()
    lateinit var primaryListMissingLetterRecyclerAdapter: MissingLetterRecyclerAdapter
    lateinit var secondaryListMissingLetterRecyclerAdapter: MissingLetterRecyclerAdapter
    lateinit var emptyLetterRecyclerAdapter: EmptyLetterRecyclerAdapter
    var originWord: String = ""

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_connect_word, container, false)

        binding.backwardImg.setOnClickListener {
            findNavController().popBackStack()
        }

        arguments?.let {
            val setWord = it.get(ChoosingModeFragment.KEY_ARGS_SET)
            if (setWord is WordSet) {
                binding.titleToolBar.text = "WORD SET NUMBER ${setWord.setNth}"
            }
        }
        // TODO("keep missing word position,hint: image")
        //TODO FAKING HERE
        originWord = "BEAUTIFUL"
        val shuffleWord = shuffleString(originWord)
        shuffleWord.forEachIndexed() { index, letter ->
            if (index < MAXLENGTH) {
                primaryListMissingLetter.add(letter.toString())
            } else {
                secondaryListMissingLetter.add(letter.toString())
            }
            listEmptyLetter.add("")
        }
        emptyLetterRecyclerAdapter =
            EmptyLetterRecyclerAdapter(listEmptyLetter,
                primaryListMissingLetter,
                secondaryListMissingLetter) { clickedLetter, position ->
//                Toast.makeText(context, "EMPTY LIST STRING IS $listEmptyLetter", Toast.LENGTH_SHORT)
//                    .show()
                if (clickedLetter == " " || clickedLetter == "") {
                    return@EmptyLetterRecyclerAdapter
                }
                listEmptyLetter[position] = ""
                emptyLetterRecyclerAdapter.notifyItemChanged(position)
                if (primaryListMissingLetter.size < 5) {
                    primaryListMissingLetter.add(clickedLetter)
                    primaryListMissingLetterRecyclerAdapter.notifyDataSetChanged()
                } else {
                    secondaryListMissingLetter.add(clickedLetter)
                    secondaryListMissingLetterRecyclerAdapter.notifyDataSetChanged()
                }
            }
        primaryListMissingLetterRecyclerAdapter =
            MissingLetterRecyclerAdapter(primaryListMissingLetter) { clickedLetter ->
                primaryListMissingLetter.remove(clickedLetter)
                primaryListMissingLetterRecyclerAdapter.notifyDataSetChanged()
                run loop@{
                    listEmptyLetter.forEachIndexed() { index, letter ->
                        if (letter == "") {
                            listEmptyLetter[index] = clickedLetter
                            emptyLetterRecyclerAdapter.notifyItemChanged(index)
                            return@loop
                        }
                    }
                }
            }

        secondaryListMissingLetterRecyclerAdapter =
            MissingLetterRecyclerAdapter(secondaryListMissingLetter) { clickedLetter ->
                secondaryListMissingLetter.remove(clickedLetter)
                secondaryListMissingLetterRecyclerAdapter.notifyDataSetChanged()
                run loop@{
                    listEmptyLetter.forEachIndexed() { index, letter ->
                        if (letter == "") {
                            listEmptyLetter[index] = clickedLetter
                            emptyLetterRecyclerAdapter.notifyItemChanged(index)
                            return@loop
                        }
                    }
                }
            }
        binding.listEmptyLetterRc.adapter = emptyLetterRecyclerAdapter
        binding.primaryListMissingLetterRc.adapter = primaryListMissingLetterRecyclerAdapter
        binding.secondaryListMissingLetterRc.adapter = secondaryListMissingLetterRecyclerAdapter
        binding.checkBtn.setOnClickListener {
            if (listEmptyLetter.joinToString().replace(",", "").replace(" ", "") == originWord) {
                Toast.makeText(context, "CORRECT", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "INCORRECT", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    private fun shuffleString(string: String): String {
        val shuffledListCharacter = string.toList().shuffled()
        val shuffledString = shuffledListCharacter.joinToString("")
        return shuffledString
    }

    private val MAXLENGTH = 5
}