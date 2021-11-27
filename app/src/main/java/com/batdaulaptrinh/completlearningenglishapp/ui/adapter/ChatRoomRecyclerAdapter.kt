package com.batdaulaptrinh.completlearningenglishapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.ChatRoomRowBinding
import com.batdaulaptrinh.completlearningenglishapp.model.ChatRoom

class ChatRoomRecyclerAdapter(private val listChatRoom: ArrayList<ChatRoom>) :
    RecyclerView.Adapter<ChatRoomRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ChatRoomRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun binding(chatRoom: ChatRoom) {
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = DataBindingUtil.inflate<ChatRoomRowBinding>(
            LayoutInflater.from(parent.context),
            R.layout.chat_room_row,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding(listChatRoom[position])
    }

    override fun getItemCount() = listChatRoom.size

    fun setListChatRoom(newListChat: List<ChatRoom>) {
        listChatRoom.clear()
        listChatRoom.addAll(newListChat)
    }
}