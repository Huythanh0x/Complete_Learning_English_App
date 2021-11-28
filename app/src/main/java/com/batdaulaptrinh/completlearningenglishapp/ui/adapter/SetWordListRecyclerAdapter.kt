package com.batdaulaptrinh.completlearningenglishapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.WordRowInWordsetBinding
import com.batdaulaptrinh.completlearningenglishapp.model.Word

class SetWordListRecyclerAdapter(
    val listWord: ArrayList<Word>,
    val clickWordListener: (word: Word) -> Unit,
    val clickSpeakerListener: (word: Word) -> Unit,
    val clickStarListener: (word: Word) -> Unit,
) :
    RecyclerView.Adapter<SetWordListRecyclerAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: WordRowInWordsetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            word: Word,
            clickWordListener: (word: Word) -> Unit,
            clickSpeakerListener: (word: Word) -> Unit,
            clickStarListener: (word: Word) -> Unit,
        ) {
            binding.apiTxt.text = word.api_uk
            binding.descriptionTxt.text = word.definition
            binding.thumbnailImg.setImageResource(R.drawable.clean_thumbnail_example)
            binding.enWordText.text = word.en_word
            binding.playSoundImg.setOnClickListener { clickSpeakerListener(word) }
            binding.root.setOnClickListener { clickWordListener(word) }
            binding.isFavouriteStarImg.setOnClickListener { clickStarListener }
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
        holder.bind(listWord[position], clickWordListener, clickSpeakerListener, clickStarListener)
    }

    override fun getItemCount() = listWord.size
}