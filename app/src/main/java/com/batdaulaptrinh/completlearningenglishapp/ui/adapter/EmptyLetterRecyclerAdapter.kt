package com.batdaulaptrinh.completlearningenglishapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.EmptyLetterRowBinding

class EmptyLetterRecyclerAdapter(
    private val listEmptyLetter: ArrayList<String>,
    private val primaryListMissingLetter: ArrayList<String>,
    private val secondaryListMissingLetter: ArrayList<String>,
    private val clickLetterListener: (letter: String, position: Int) -> Unit,
) :
    RecyclerView.Adapter<EmptyLetterRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: EmptyLetterRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun binding(
            letter: String,
            position: Int,
            clickLetterListener: (letter: String, position: Int) -> Unit,
        ) {
            binding.missingLetterTxt.text = letter
            binding.underscoreTxt.text = "_"
            binding.root.setOnClickListener {
                clickLetterListener(letter, position)
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MyViewHolder {
        val binding = DataBindingUtil.inflate<EmptyLetterRowBinding>(
            LayoutInflater.from(parent.context),
            R.layout.empty_letter_row,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding(listEmptyLetter[position], position, clickLetterListener)
    }

    override fun getItemCount() = listEmptyLetter.size

    fun setListChatRoom(newListChat: List<String>) {
        listEmptyLetter.clear()
        listEmptyLetter.addAll(newListChat)
    }
}