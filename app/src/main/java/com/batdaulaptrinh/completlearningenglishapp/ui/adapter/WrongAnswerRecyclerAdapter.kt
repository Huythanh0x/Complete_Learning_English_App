package com.batdaulaptrinh.completlearningenglishapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.WrongPasswordDialogBinding
import com.batdaulaptrinh.completlearningenglishapp.model.Word

class WrongAnswerRecyclerAdapter(private val listWrongAnswerRecycler: ArrayList<Word>) :
    RecyclerView.Adapter<WrongAnswerRecyclerAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: WrongPasswordDialogBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(word: Word) {

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WrongAnswerRecyclerAdapter.MyViewHolder {
        val binding = DataBindingUtil.inflate<WrongPasswordDialogBinding>(
            LayoutInflater.from(parent.context),
            R.layout.wrong_answer_row, parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WrongAnswerRecyclerAdapter.MyViewHolder, position: Int) {
        holder.bind(listWrongAnswerRecycler[position])
    }

    override fun getItemCount() = listWrongAnswerRecycler.size
}