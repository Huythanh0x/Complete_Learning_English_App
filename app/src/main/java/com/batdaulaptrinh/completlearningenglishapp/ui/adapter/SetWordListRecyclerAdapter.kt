package com.batdaulaptrinh.completlearningenglishapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.data.sharedPreferences.SharePreferencesProvider
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
            val sharePreferencesProvider = SharePreferencesProvider(binding.root.context)
            val preferAccent = sharePreferencesProvider.getPreferAccent()
            binding.preferAccent = preferAccent
            binding.word = word
            binding.root.setOnClickListener { clickWordListener(word) }
            binding.isFavouriteStarImg.setOnClickListener {
                if (word.is_favourite == 0) {
                    binding.isFavouriteStarImg.isChecked = true
                    word.is_favourite = 1
                    binding.isFavouriteStarImg.playAnimation()
                } else {
                    word.is_favourite = 0
                    binding.isFavouriteStarImg.isChecked = false
                    binding.isFavouriteStarImg.playAnimation()
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
            DataBindingUtil.inflate<WordRowInWordsetBinding>(
                LayoutInflater.from(parent.context),
                R.layout.word_row_in_wordset,
                parent,
                false
            )
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