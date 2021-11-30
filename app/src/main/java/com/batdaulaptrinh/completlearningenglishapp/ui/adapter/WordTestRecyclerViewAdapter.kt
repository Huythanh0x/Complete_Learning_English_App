package com.batdaulaptrinh.completlearningenglishapp.ui.adapter

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.WordTestRowBinding
import com.batdaulaptrinh.completlearningenglishapp.model.WordSet

class WordTestRecyclerViewAdapter(
    private val listWordTest: ArrayList<WordSet>,
    val clickListener: (wordSet: WordSet) -> Unit
) :
    RecyclerView.Adapter<WordTestRecyclerViewAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: WordTestRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(wordSet: WordSet, clickListener: (wordSet: WordSet) -> Unit) {
            val decodedString: ByteArray = Base64.decode(wordSet.imageOfSet, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            binding.thumbnailImg.setImageBitmap(decodedByte)
            binding.wordSetNumberTxt.text = "Word set number ${wordSet.setNth + 1}"
            binding.testBtn.setOnClickListener{
                clickListener(wordSet)
            }
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
        holder.bind(listWordTest[position], clickListener)
    }

    override fun getItemCount() = listWordTest.size
}