package com.batdaulaptrinh.completlearningenglishapp.ui.adapter

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.WordSetRowBinding
import com.batdaulaptrinh.completlearningenglishapp.model.WordSet
import kotlin.random.Random


class WordSetRecyclerAdapter(
    private val listWordSet: ArrayList<WordSet>,
    private val clickListener: (wordSet: WordSet) -> Unit
) :
    RecyclerView.Adapter<WordSetRecyclerAdapter.MyViewHolder>() {
    class MyViewHolder(
        val binding: WordSetRowBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(
            wordSet: WordSet,
            clickListener: (wordSet: WordSet) -> Unit,
        ) {
            val progress = Random.nextInt(20)
            binding.progressPb.progress = progress
            "$progress/20".also { binding.progressTxt.text = it }
            val decodedString: ByteArray = Base64.decode(wordSet.imageOfSet, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            binding.thumbnailImg.setImageBitmap(decodedByte)
            binding.wordSetNumberTxt.text = "Word set number ${wordSet.setNth + 1}"
            binding.root.setOnClickListener {
                clickListener(wordSet)
            }
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
        holder.bind(listWordSet[position], clickListener)
    }

    override fun getItemCount() = listWordSet.size

}
