package com.batdaulaptrinh.completlearningenglishapp.ui.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.ReceiveMessageRowBinding
import com.batdaulaptrinh.completlearningenglishapp.databinding.SendMessageRowBinding
import com.batdaulaptrinh.completlearningenglishapp.model.Message

class MessageRecyclerAdapter(private val listMessage: ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val TYPE_SEND = 1
    val TYPE_RECEIVE = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_SEND) {
            val binding =
                DataBindingUtil.inflate<SendMessageRowBinding>(LayoutInflater.from(parent.context),
                    R.layout.send_message_row,
                    parent,
                    false)
            SendMessageViewHolder(binding)
        } else {
            val binding =
                DataBindingUtil.inflate<ReceiveMessageRowBinding>(LayoutInflater.from(parent.context),
                    R.layout.receive_message_row,
                    parent,
                    false)
            ReceiveMessageViewHolder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (listMessage[position].isOwner == true)
            TYPE_SEND
        else
            TYPE_RECEIVE

    }

    override fun getItemCount() = listMessage.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_SEND) {
            val castHolder = holder as SendMessageViewHolder
            castHolder.bind(listMessage[position])
        } else {
            val castHolder = holder as ReceiveMessageViewHolder
            castHolder.bind(listMessage[position])
        }
    }

    fun setList(newList: List<Message>) {
        listMessage.clear()
        listMessage.addAll(newList)
        notifyDataSetChanged()
    }

    fun addMessage(newMessage: Message) {
        listMessage.add(newMessage)
        notifyItemInserted(listMessage.size - 1)
    }
}

class SendMessageViewHolder(val binding: SendMessageRowBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(message: Message) {
        //TODO FAKE URI NOT URL
        if (message.text != null) {
            binding.tvMessage.text = message.text
            binding.imageMessage.visibility = View.GONE
        } else {
            binding.tvMessage.visibility = View.GONE
            binding.imageMessage.setImageURI(Uri.parse(message.photoUrl))
//            Log.d("TAG SEND MESSAGE", "message is not null")
        }
    }
}

class ReceiveMessageViewHolder(val binding: ReceiveMessageRowBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(message: Message) {
        binding.imageProfileImg.setImageResource(R.drawable.avatar_example)
        if (message.text != null) {
            binding.tvMessage.text = message.text
            binding.imageMessage.visibility = View.GONE
        } else if (message.photoUrl != null) {
            binding.tvMessage.visibility = View.GONE
            binding.imageMessage.setImageURI(Uri.parse(message.photoUrl))
        }
    }
}