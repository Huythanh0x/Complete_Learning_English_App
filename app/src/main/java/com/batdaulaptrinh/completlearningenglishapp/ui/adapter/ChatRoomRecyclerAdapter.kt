package com.batdaulaptrinh.completlearningenglishapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.ChatRoomRowBinding
import com.batdaulaptrinh.completlearningenglishapp.model.ChatRoom
import com.batdaulaptrinh.completlearningenglishapp.utils.Utils
import kotlin.random.Random

class ChatRoomRecyclerAdapter(
    private val listChatRoom: ArrayList<ChatRoom>,
    val roomChatClickListener: (chatRoom: ChatRoom) -> Unit
) :
    RecyclerView.
    Adapter<ChatRoomRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ChatRoomRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun binding(chatRoom: ChatRoom, roomChatClickListener: (chatRoom: ChatRoom) -> Unit) {
            binding.root.setOnClickListener {
                roomChatClickListener(chatRoom)
            }
            if (Random.nextBoolean()) {
                binding.isOnlineStateImg.setImageResource(R.color.grey)
            } else {
                binding.isOnlineStateImg.setImageResource(R.color.green)
            }
            if (Random.nextBoolean()) {
                binding.sentMessageStateImg.setImageResource(R.drawable.sent_message_state_img)
            } else {
                binding.sentMessageStateImg.setImageResource(R.color.white)
            }

            binding.avatarUserImg.setImageResource(R.drawable.avatar_example)

            binding.userNameTxt.text = Utils.getRandomString(10)
            binding.messageContentTxt.text = Utils.getRandomString(20)
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
        holder.binding(listChatRoom[position], roomChatClickListener)
    }

    override fun getItemCount() = listChatRoom.size

    fun setListChatRoom(newListChat: List<ChatRoom>) {
        listChatRoom.clear()
        listChatRoom.addAll(newListChat)
    }
}