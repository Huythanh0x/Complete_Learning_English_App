package com.batdaulaptrinh.completlearningenglishapp.ui.game

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
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentConnectWordBinding
import com.batdaulaptrinh.completlearningenglishapp.ui.adapter.EmptyLetterRecyclerAdapter
import com.batdaulaptrinh.completlearningenglishapp.ui.adapter.MissingLetterRecyclerAdapter
import java.lang.StringBuilder
import java.util.*
import java.util.function.Consumer
import java.util.stream.Collectors

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


        //TODO FAKING HERE
        originWord = "BEAUTIFUL"
        val shuffleWord = shuffleString(originWord)
        shuffleWord?.forEachIndexed() { index, letter ->
            if (index < MAX_LENGTH) {
                primaryListMissingLetter.add(letter.toString())
            } else {
                secondaryListMissingLetter.add(letter.toString())
            }
            listEmptyLetter.add("")
        }
        Log.d("TAG LIST SIZE PRIMARY", primaryListMissingLetter.size.toString())
        Log.d("TAG LIST SIZE SECOND", secondaryListMissingLetter.size.toString())
        emptyLetterRecyclerAdapter =
            EmptyLetterRecyclerAdapter(listEmptyLetter,
                primaryListMissingLetter,
                secondaryListMissingLetter) { clickedLetter, position ->
//                Toast.makeText(context, "EMPTY LIST STRING IS $listEmptyLetter", Toast.LENGTH_SHORT)
//                    .show()
                if(clickedLetter == " " || clickedLetter == ""){
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
    @RequiresApi(Build.VERSION_CODES.N)
    fun shuffleString(string: String): String? {
        val list: List<Char?> = string.chars().mapToObj { c: Int -> c.toChar() }
            .collect(Collectors.toList())
        Collections.shuffle(list)
        val sb = StringBuilder()
        list.forEach(Consumer { c: Char? -> sb.append(c) })
        return sb.toString()
    }

    private val MAX_LENGTH = 5
}