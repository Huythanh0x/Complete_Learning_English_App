package com.batdaulaptrinh.completlearningenglishapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.WordSetRowBinding
import com.batdaulaptrinh.completlearningenglishapp.model.WordSet

class WordSetRecyclerAdapter(val listWordSet: ArrayList<WordSet>) :
    RecyclerView.Adapter<WordSetRecyclerAdapter.MyViewHolder>() {
    class MyViewHolder(binding: WordSetRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(wordSet: WordSet) {

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = DataBindingUtil.inflate<WordSetRowBinding>(
            LayoutInflater.from(parent.context),
            R.layout.word_set_row, parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listWordSet[position])
    }

    override fun getItemCount() = listWordSet.size
}