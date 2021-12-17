package com.batdaulaptrinh.completlearningenglishapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.data.sharedPreferences.SharePreferencesProvider
import com.batdaulaptrinh.completlearningenglishapp.databinding.WordRowInWordlistBinding
import com.batdaulaptrinh.completlearningenglishapp.model.MinimalWord

class WordListRecyclerAdapter(
    private val listWord: ArrayList<MinimalWord>,
    private val wordClickListener: (minimalWord: MinimalWord) -> Unit,
    private val starClickListener: (minimalWord: MinimalWord) -> Unit,
) :
    RecyclerView.Adapter<WordListRecyclerAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: WordRowInWordlistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(
            minimalWord: MinimalWord,
            wordClickListener: (minimalWord: MinimalWord) -> Unit,
            starClickListener: (minimalWord: MinimalWord) -> Unit,
        ) {
            //TODO check accent
            binding.root.setOnClickListener {
                wordClickListener(minimalWord)
            }
            //TODO binding adapter cause refresh so clearly
            binding.minimalWord = minimalWord
            binding.isFavouriteStarImg.setOnClickListener {
                if (minimalWord.is_favourite == 0) {
                    minimalWord.is_favourite = 1
                    binding.isFavouriteStarImg.isChecked = true
                    binding.isFavouriteStarImg.playAnimation()
                } else {
                    minimalWord.is_favourite = 0
                    binding.isFavouriteStarImg.isChecked = false
//                    binding.isFavouriteStarImg.playAnimation()
                }
                starClickListener(minimalWord)
            }
            binding.preferAccent = SharePreferencesProvider(binding.root.context).getPreferAccent()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = DataBindingUtil.inflate<WordRowInWordlistBinding>(
            LayoutInflater.from(parent.context),
            R.layout.word_row_in_wordlist, parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding(
            listWord[position],
            wordClickListener,
            starClickListener
        )
    }

    override fun getItemCount() = listWord.size

    fun addList(newList: List<MinimalWord>) {
        listWord.clear()
        listWord.addAll(newList)
        notifyDataSetChanged()
    }
}