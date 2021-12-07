package com.batdaulaptrinh.completlearningenglishapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.SingleFlashCardItemBinding
import com.batdaulaptrinh.completlearningenglishapp.model.Word

class FlashCardAdapter(
    val listWord: ArrayList<Word>,
    val clickNextBtn: (position: Int) -> Unit,
    val clickPreviousBtn: (position: Int) -> Unit,
) :
    RecyclerView.Adapter<FlashCardAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: SingleFlashCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(word: Word) {
            binding.word = word
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            DataBindingUtil.inflate<SingleFlashCardItemBinding>(LayoutInflater.from(parent.context),
                R.layout.single_flash_card_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listWord[position])
        when (position) {
            0 -> {
                holder.binding.previousCardImg.alpha = 0.3f
                holder.binding.previousCardImg.isEnabled = false
            }
            listWord.size - 1 -> {
                holder.binding.nextCardImg.alpha = 0.3f
                holder.binding.nextCardImg.isEnabled = false
            }
            else -> {
                holder.binding.nextCardImg.alpha = 1f
                holder.binding.previousCardImg.alpha = 1f
                holder.binding.nextCardImg.isEnabled = true
                holder.binding.previousCardImg.isEnabled = true
            }
        }
        holder.binding.nextCardImg.setOnClickListener {
            clickNextBtn(position)
        }
        holder.binding.previousCardImg.setOnClickListener {
            clickPreviousBtn(position)
        }
    }

    override fun getItemCount(): Int = listWord.size

    fun setList(newList: List<Word>) {
        listWord.clear()
        listWord.addAll(newList)
        notifyDataSetChanged()
    }
}