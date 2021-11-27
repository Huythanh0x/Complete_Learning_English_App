package com.batdaulaptrinh.completlearningenglishapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.OnlineUserRowBinding
import com.batdaulaptrinh.completlearningenglishapp.model.User

class OnlineUserRecyclerAdapter(val listOnlineUsers: ArrayList<User>) :
    RecyclerView.Adapter<OnlineUserRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: OnlineUserRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OnlineUserRecyclerAdapter.MyViewHolder {
        val binding = DataBindingUtil.inflate<OnlineUserRowBinding>(
            LayoutInflater.from(parent.context),
            R.layout.online_user_row, parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OnlineUserRecyclerAdapter.MyViewHolder, position: Int) {
        holder.bind(listOnlineUsers[position])
    }

    override fun getItemCount() = listOnlineUsers.size
}