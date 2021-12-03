package com.batdaulaptrinh.completlearningenglishapp.ui.adapter

import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.WordRowInWordsetBinding
import com.batdaulaptrinh.completlearningenglishapp.model.Word
import com.bumptech.glide.Glide

class SetWordListRecyclerAdapter(
    private val listWord: ArrayList<Word>,
    private val clickWordListener: (word: Word) -> Unit,
    private val clickSpeakerListener: (word: Word) -> Unit,
    private val clickStarListener: (word: Word) -> Unit,
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
            binding.thumbnailImg.setImageResource(R.drawable.clean_thumbnail_1)
            binding.enWordText.text = word.en_word
            binding.playSoundImg.setOnClickListener { clickSpeakerListener(word) }
            binding.root.setOnClickListener { clickWordListener(word) }
            try {
                val decodedString: ByteArray = Base64.decode(word.thumbnail, Base64.DEFAULT)
                val bitmap =
                    BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

                Glide.with(binding.root)
                    .load(bitmap) // image url
                    .placeholder(R.drawable.app_logo_img) // any placeholder to load at start
                    .error(R.drawable.app_logo_img)  // any image in case of error
                    .centerCrop()
                    .into(binding.thumbnailImg);  // imageview object

            } catch (e: Exception) {
                Log.e("LOAD IMAGE", e.toString())
            }
            if (word.is_favourite == 0) {
                binding.isFavouriteStarImg.setImageResource(R.drawable.ic_baseline_star_border_24)
            } else {
                binding.isFavouriteStarImg.setImageResource(R.drawable.ic_baseline_star_24)
            }
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
        holder.bind(listWord[position], clickWordListener, clickSpeakerListener, clickStarListener)
    }

    override fun getItemCount() = listWord.size

    fun addList(newList: List<Word>) {
        listWord.clear()
        listWord.addAll(newList)
        notifyDataSetChanged()
    }
}