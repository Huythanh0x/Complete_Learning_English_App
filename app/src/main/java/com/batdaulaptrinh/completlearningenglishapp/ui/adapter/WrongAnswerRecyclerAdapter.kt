package com.batdaulaptrinh.completlearningenglishapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.WrongAnswerRowBinding
import com.batdaulaptrinh.completlearningenglishapp.databinding.WrongPasswordDialogBinding
import com.batdaulaptrinh.completlearningenglishapp.model.Word

class WrongAnswerRecyclerAdapter(private val listWrongAnswer: ArrayList<Word>) :
    RecyclerView.Adapter<WrongAnswerRecyclerAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: WrongAnswerRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(word: Word) {
            binding.wrongAnswerTxt.text = word.en_word
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = DataBindingUtil.inflate<WrongAnswerRowBinding>(
            LayoutInflater.from(parent.context),
            R.layout.wrong_answer_row, parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listWrongAnswer[position])
    }

    override fun getItemCount() = listWrongAnswer.size
}