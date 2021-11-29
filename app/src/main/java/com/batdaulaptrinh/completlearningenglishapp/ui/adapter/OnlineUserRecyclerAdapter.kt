package com.batdaulaptrinh.completlearningenglishapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.OnlineUserRowBinding
import com.batdaulaptrinh.completlearningenglishapp.model.ChatRoom
import com.batdaulaptrinh.completlearningenglishapp.utils.Utils

class OnlineUserRecyclerAdapter(
    private val listOnlineUserChatRooms: ArrayList<ChatRoom>,
    private val clickOnlineUserChatRoomListener: (chatRoom: ChatRoom) -> Unit,
) :
    RecyclerView.Adapter<OnlineUserRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: OnlineUserRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            onlineUserChatRoom: ChatRoom,
            clickOnlineUserChatRoomListener: (chatRoom: ChatRoom) -> Unit,
        ) {
            binding.avatarImg.setImageResource(R.drawable.avatar_example)
            binding.isOnlineImg.setImageResource(R.color.green)
            binding.nameTxt.text = Utils.getRandomString(7)
            binding.root.setOnClickListener {
                clickOnlineUserChatRoomListener(onlineUserChatRoom)
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MyViewHolder {
        val binding = DataBindingUtil.inflate<OnlineUserRowBinding>(
            LayoutInflater.from(parent.context),
            R.layout.online_user_row, parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listOnlineUserChatRooms[position], clickOnlineUserChatRoomListener)
    }

    override fun getItemCount() = listOnlineUserChatRooms.size
}