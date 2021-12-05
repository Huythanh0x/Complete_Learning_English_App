package com.batdaulaptrinh.completlearningenglishapp.ui.chat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentDMChatBinding
import com.batdaulaptrinh.completlearningenglishapp.model.ChatRoom
import com.batdaulaptrinh.completlearningenglishapp.ui.adapter.MessageRecyclerAdapter
import com.batdaulaptrinh.completlearningenglishapp.utils.Utils

class DMChatFragment : Fragment() {
    lateinit var binding: FragmentDMChatBinding

    companion object {
        val KEY_CHAT_HEADER = "KEY_CHAT_HEADER"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_d_m_chat, container, false)

        binding.backwardImg.setOnClickListener {
            findNavController().popBackStack()
        }
        arguments?.let {
            val chatRoom = it.get(KEY_CHAT_HEADER)
            if (chatRoom is ChatRoom) {
                binding.titleToolBar.text = chatRoom.receiverId.toString()
                binding.sendMessageImg.setOnClickListener {
                    Toast.makeText(context,
                        "Send message ${binding.messageInputEdt.text}",
                        Toast.LENGTH_SHORT).show()
                    binding.messageInputEdt.text?.clear()
                }
            }
        }

        binding.sendPictureImg.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE)
        }

        //TODO FAKING HERE
        val listMessage = Utils.getListMessage()
        binding.messagesRc.adapter = MessageRecyclerAdapter(listMessage)
        binding.messagesRc.scrollToPosition(listMessage.size - 1)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE) {
            if (data != null) {
                Log.d("TAG IMAGE", data.data.toString())
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    val REQUEST_CODE = 19343
}