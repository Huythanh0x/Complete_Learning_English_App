package com.batdaulaptrinh.completlearningenglishapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.EntranceQuestionRowBinding
import com.batdaulaptrinh.completlearningenglishapp.model.MinimalWord

class EntranceQuestionRecyclerAdapter(
    val listWord: ArrayList<MinimalWord>,
    private val listAnswer: ArrayList<Boolean>,
    private val checkBoxListener: (position: Int, isCheck: Boolean) -> Unit,
) :
    RecyclerView.Adapter<EntranceQuestionRecyclerAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: EntranceQuestionRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            minimalWord: MinimalWord,
            listAnswer: ArrayList<Boolean>,
            position: Int,
        ) {
            binding.answerTxt.text = minimalWord.en_word
            binding.answerCb.isChecked = listAnswer[position]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            DataBindingUtil.inflate<EntranceQuestionRowBinding>(LayoutInflater.from(parent.context),
                R.layout.entrance_question_row, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listWord[position], listAnswer, position)
        holder.binding.answerCb.setOnClickListener {
            checkBoxListener(position, holder.binding.answerCb.isChecked)
        }
        holder.binding.answerTxt.setOnClickListener {
            holder.binding.answerCb.isChecked = true
            checkBoxListener(position, holder.binding.answerCb.isChecked)
        }
    }

    override fun getItemCount(): Int = listWord.size

    fun setListWord(newListWord: List<MinimalWord>?) {
        if (newListWord != null) {
            listWord.clear()
            listWord.addAll(newListWord)
            notifyDataSetChanged()
        }
    }

    fun setListAnswer(newListAnswer: List<Boolean>) {
        listAnswer.clear()
        listAnswer.addAll(newListAnswer)
        notifyDataSetChanged()
    }
}