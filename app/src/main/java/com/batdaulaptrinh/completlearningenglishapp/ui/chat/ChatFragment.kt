package com.batdaulaptrinh.completlearningenglishapp.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentChatBinding
import com.batdaulaptrinh.completlearningenglishapp.model.ChatRoom
import com.batdaulaptrinh.completlearningenglishapp.ui.adapter.ChatRoomRecyclerAdapter
import com.batdaulaptrinh.completlearningenglishapp.ui.adapter.OnlineUserRecyclerAdapter
import com.batdaulaptrinh.completlearningenglishapp.utils.Utils

class ChatFragment : Fragment() {
    lateinit var binding: FragmentChatBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)

        //TODO faking here
        binding.listChatRoomRv.adapter =
            ChatRoomRecyclerAdapter(Utils.getListChatRoom()) { chatRoom: ChatRoom ->
                findNavController().navigate(R.id.action_navigation_chat_to_DMChatFragment,
                    bundleOf(
                        DMChatFragment.KEY_CHAT_HEADER to chatRoom))

            }
        binding.onlineUserListRv.adapter =
            OnlineUserRecyclerAdapter(Utils.getListChatRoom()) { chatRoom ->
                findNavController().navigate(R.id.action_navigation_chat_to_DMChatFragment,
                    bundleOf(
                        DMChatFragment.KEY_CHAT_HEADER to chatRoom))
            }
        //TODO("new fragment and show suggestion")
        return binding.root
    }
}