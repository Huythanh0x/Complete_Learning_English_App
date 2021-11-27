package com.batdaulaptrinh.completlearningenglishapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.MessageRowBinding
import com.batdaulaptrinh.completlearningenglishapp.model.Message

class MessageRecyclerAdapter(private val listMessage: ArrayList<Message>) :
    RecyclerView.Adapter<MessageRecyclerAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: MessageRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MessageRecyclerAdapter.MyViewHolder {
        val binding = DataBindingUtil.inflate<MessageRowBinding>(
            LayoutInflater.from(parent.context),
            R.layout.message_row, parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageRecyclerAdapter.MyViewHolder, position: Int) {
        holder.bind(listMessage[position])
    }

    override fun getItemCount() = listMessage.size
}