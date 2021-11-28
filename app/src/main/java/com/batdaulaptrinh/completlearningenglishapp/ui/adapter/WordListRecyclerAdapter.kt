package com.batdaulaptrinh.completlearningenglishapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.WordRowInWordlistBinding
import com.batdaulaptrinh.completlearningenglishapp.model.Word
import kotlin.random.Random

class WordListRecyclerAdapter(
    private val listWord: ArrayList<Word>,
    private val speakerClickListener: (word: Word) -> Unit,
    private val wordClickListener: (word: Word) -> Unit,
    private val starClickListener: (word: Word) -> Unit
) :
    RecyclerView.Adapter<WordListRecyclerAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: WordRowInWordlistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(
            word: Word,
            speakerClickListener: (word: Word) -> Unit,
            wordClickListener: (word: Word) -> Unit,
            starClickListener: (word: Word) -> Unit
        ) {
            binding.apiTxt.text = word.api_uk
            binding.enWordText.text = word.en_word
            val isStar = Random.nextInt(100) % 2 == 0
            if (isStar) {
                binding.isFavouriteStarImg.setImageResource(R.drawable.ic_baseline_star_24)
                binding.isFavouriteStarImg.alpha = 1.0f
            } else {
                binding.isFavouriteStarImg.setImageResource(R.drawable.ic_baseline_star_border_24)
                binding.isFavouriteStarImg.alpha = 0.9f
            }
            binding.playSoundImg.setOnClickListener {
                speakerClickListener(word)
            }
            binding.enWordText.setOnClickListener {
                wordClickListener(word)
            }

            //TODO add field favorite and update database
            binding.isFavouriteStarImg.setOnClickListener {
                if (binding.isFavouriteStarImg.alpha != 1.0f) {
                    binding.isFavouriteStarImg.setImageResource(R.drawable.ic_baseline_star_24)
                    binding.isFavouriteStarImg.alpha = 1.0f
                } else {
                    binding.isFavouriteStarImg.setImageResource(R.drawable.ic_baseline_star_border_24)
                    binding.isFavouriteStarImg.alpha = 0.9f
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
}