package com.batdaulaptrinh.completlearningenglishapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.MissingLetterRowBinding
import okhttp3.internal.notify

class MissingLetterRecyclerAdapter(
    private val listMissingLetter: ArrayList<String>,
    private val clickButtonListener: (letter: String) -> Unit,
) :
    RecyclerView.Adapter<MissingLetterRecyclerAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: MissingLetterRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(s: String, clickButtonListener: (letter: String) -> Unit) {
            binding.missingLetterBtn.text = s
            binding.missingLetterBtn.setOnClickListener {
                view->
//                view.visibility = View.INVISIBLE
                clickButtonListener(s)

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = DataBindingUtil.inflate<MissingLetterRowBinding>(
            LayoutInflater.from(parent.context),
            R.layout.missing_letter_row, parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listMissingLetter[position], clickButtonListener)
    }

    override fun getItemCount() = listMissingLetter.size

    fun setList(newListLetter: ArrayList<String>){
        listMissingLetter.clear()
        listMissingLetter.addAll(newListLetter)
        notifyDataSetChanged()
    }

}