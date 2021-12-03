package com.batdaulaptrinh.completlearningenglishapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.WordRowInWordsetBinding
import com.batdaulaptrinh.completlearningenglishapp.model.Word

class SetWordListRecyclerAdapter(
    private val listWord: ArrayList<Word>,
    private val clickWordListener: (word: Word) -> Unit,
    private val clickStarListener: (word: Word) -> Unit,
) :
    RecyclerView.Adapter<SetWordListRecyclerAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: WordRowInWordsetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            word: Word,
            clickWordListener: (word: Word) -> Unit,
            clickStarListener: (word: Word) -> Unit,
        ) {
            binding.word = word
            binding.root.setOnClickListener { clickWordListener(word) }
            binding.isFavouriteStarImg.setOnClickListener {
                if (word.is_favourite == 0) {
                    binding.isFavouriteStarImg.setImageResource(R.drawable.ic_baseline_star_24)
                    word.is_favourite = 1
                } else {
                    word.is_favourite = 0
                    binding.isFavouriteStarImg.setImageResource(R.drawable.ic_baseline_star_border_24)
                }
                clickStarListener(word)
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MyViewHolder {
        val binding =
            DataBindingUtil.inflate<WordRowInWordsetBinding>(LayoutInflater.from(parent.context),
                R.layout.word_row_in_wordset,
                parent,
                false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listWord[position], clickWordListener, clickStarListener)
    }

    override fun getItemCount() = listWord.size

    fun addList(newList: List<Word>) {
        listWord.clear()
        listWord.addAll(newList)
        notifyDataSetChanged()
    }
}