package com.batdaulaptrinh.completlearningenglishapp.ui.chat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentDMChatBinding
import com.batdaulaptrinh.completlearningenglishapp.model.ChatRoom
import com.batdaulaptrinh.completlearningenglishapp.model.Message
import com.batdaulaptrinh.completlearningenglishapp.ui.adapter.MessageRecyclerAdapter

class DMChatFragment : Fragment() {
    lateinit var binding: FragmentDMChatBinding
    lateinit var dmChatViewModel: DMChatViewModel
    lateinit var adapter: MessageRecyclerAdapter

    companion object {
        val KEY_CHAT_HEADER = "KEY_CHAT_HEADER"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_d_m_chat, container, false)
        val dmChatViewModelFactory = DMChatViewModelFactory(requireActivity().application)
        dmChatViewModel =
            ViewModelProvider(this, dmChatViewModelFactory)[DMChatViewModel::class.java]
        binding.backwardImg.setOnClickListener {
            findNavController().popBackStack()
        }
        arguments?.let {
            val chatRoom = it.get(KEY_CHAT_HEADER)
            if (chatRoom is ChatRoom) {
                binding.titleToolBar.text = chatRoom.receiverId ?: "Dinh Son Pro"
            }
        }
        adapter = MessageRecyclerAdapter(arrayListOf())
        dmChatViewModel.getFakeListMessage()
        dmChatViewModel.listMessage.observe(viewLifecycleOwner) { listMessage ->
            Log.d("TAG NEW LIST UPDATE", listMessage.size.toString())
            adapter.setList(listMessage)
            binding.messagesRv.scrollToPosition(listMessage.size - 1)
        }
        binding.messagesRv.adapter = adapter
        binding.sendMessageImg.setOnClickListener {
            dmChatViewModel.addMessage(Message(null,
                null,
                null,
                true,
                null,
                null,
                null,
                null,
                null,
                binding.messageInputEdt.text.toString(),
                null,
                null,
                false))
            binding.messageInputEdt.text?.clear()
        }
        binding.sendPictureImg.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE)
        }
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE) {
            if (data != null) {
//                Log.d("TAG IMAGE", data.data.toString())
                dmChatViewModel.addMessage(Message(null,
                    null,
                    null,
                    true,
                    null,
                    data.dataString,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    false))
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    val REQUEST_CODE = 19343
}