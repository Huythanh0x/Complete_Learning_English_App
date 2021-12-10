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
    val checkBoxListener: (position: Int, isCheck: Boolean) -> Unit,
) :
    RecyclerView.Adapter<EntranceQuestionRecyclerAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: EntranceQuestionRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            minimalWord: MinimalWord,
        ) {
            binding.answerTxt.text = minimalWord.en_word
            binding.answerTxt.setOnClickListener {
                binding.answerCb.isChecked = !binding.answerCb.isChecked
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            DataBindingUtil.inflate<EntranceQuestionRowBinding>(LayoutInflater.from(parent.context),
                R.layout.entrance_question_row, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listWord[position])
        holder.binding.answerCb.setOnCheckedChangeListener { _, isCheck ->
            checkBoxListener(position, isCheck)
        }
    }

    override fun getItemCount(): Int = listWord.size

    fun setList(newListWord: List<MinimalWord>?) {
        if (newListWord != null) {
            listWord.clear()
            listWord.addAll(newListWord)
            notifyDataSetChanged()
        }
    }
}