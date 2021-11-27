package com.batdaulaptrinh.completlearningenglishapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.WordTestRowBinding
import com.batdaulaptrinh.completlearningenglishapp.model.WordSet

class WordTestRecyclerViewAdapter(private val listWordTest: ArrayList<WordSet>) :
    RecyclerView.Adapter<WordTestRecyclerViewAdapter.MyViewHolder>() {
    class MyViewHolder(binding: WordTestRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(wordSet: WordSet) {

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = DataBindingUtil.inflate<WordTestRowBinding>(
            LayoutInflater.from(parent.context),
            R.layout.word_test_row, parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listWordTest[position])
    }

    override fun getItemCount() = listWordTest.size
}