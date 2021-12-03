package com.batdaulaptrinh.completlearningenglishapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.WordRowInWordlistBinding
import com.batdaulaptrinh.completlearningenglishapp.model.MinimalWord

class WordListRecyclerAdapter(
    private val listWord: ArrayList<MinimalWord>,
    private val speakerClickListener: (word: MinimalWord) -> Unit,
    private val wordClickListener: (word: MinimalWord) -> Unit,
    private val starClickListener: (word: MinimalWord) -> Unit,
) :
    RecyclerView.Adapter<WordListRecyclerAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: WordRowInWordlistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(
            word: MinimalWord,
            speakerClickListener: (word: MinimalWord) -> Unit,
            wordClickListener: (word: MinimalWord) -> Unit,
            starClickListener: (word: MinimalWord) -> Unit,
        ) {
            if (word.is_favourite == 1) {
                binding.isFavouriteStarImg.setImageResource(R.drawable.ic_baseline_star_24)
            } else {
                binding.isFavouriteStarImg.setImageResource(R.drawable.ic_baseline_star_border_24)
            }
            //TODO check accent
            binding.playSoundImg.setImageResource(R.drawable.us_speaker_ic)
            binding.apiTxt.text = word.api_us
            binding.enWordText.text = word.en_word
            binding.playSoundImg.setOnClickListener {
                speakerClickListener(word)
            }
            binding.root.setOnClickListener {
                wordClickListener(word)
            }

            binding.isFavouriteStarImg.setOnClickListener {
                if (word.is_favourite == 0) {
                    word.is_favourite = 1
                    binding.isFavouriteStarImg.setImageResource(R.drawable.ic_baseline_star_24)
                } else {
                    word.is_favourite = 0
                    binding.isFavouriteStarImg.setImageResource(R.drawable.ic_baseline_star_border_24)
                }
                starClickListener(word)
            }
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
            speakerClickListener,
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