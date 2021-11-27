package com.batdaulaptrinh.completlearningenglishapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.WordRowInWordlistBinding
import com.batdaulaptrinh.completlearningenglishapp.model.Word

class WordListRecyclerAdapter(val listWord: ArrayList<Word>) :
    RecyclerView.Adapter<WordListRecyclerAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: WordRowInWordlistBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = DataBindingUtil.inflate<WordRowInWordlistBinding>(
            LayoutInflater.from(parent.context),
            R.layout.word_row_in_wordlist, parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount() = listWord.size
}