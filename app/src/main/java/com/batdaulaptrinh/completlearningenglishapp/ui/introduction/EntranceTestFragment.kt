package com.batdaulaptrinh.completlearningenglishapp.ui.introduction

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.data.database.LearningAppDatabase
import com.batdaulaptrinh.completlearningenglishapp.databinding.EntranceResultDialogBinding
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentEntranceTestBinding
import com.batdaulaptrinh.completlearningenglishapp.repository.WordRepository
import com.batdaulaptrinh.completlearningenglishapp.ui.adapter.EntranceQuestionRecyclerAdapter

class EntranceTestFragment : Fragment() {
    lateinit var binding: FragmentEntranceTestBinding
    lateinit var adapter: EntranceQuestionRecyclerAdapter
    private lateinit var entranceTestViewModel: EntranceTestViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_entrance_test, container, false)
        val wordDao = LearningAppDatabase.getInstance(requireContext()).wordDao
        val wordRepository = WordRepository(wordDao)
        val entranceTestViewModelFactory =
            EntranceTestViewModelFactory(requireActivity().application, wordRepository)
        entranceTestViewModel =
            ViewModelProvider(this, entranceTestViewModelFactory)[EntranceTestViewModel::class.java]
        entranceTestViewModel.getListWord()
        adapter = EntranceQuestionRecyclerAdapter(arrayListOf()) { position, isCheck ->
            entranceTestViewModel.checkAt(position, isCheck)
        }
        entranceTestViewModel.listWord.observe(viewLifecycleOwner) { listMinimalWord ->
            if (listMinimalWord.isEmpty()) {
                binding.progressCl.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.INVISIBLE
            } else {
                binding.recyclerView.visibility = View.VISIBLE
                binding.progressCl.visibility = View.GONE
                adapter.setList(listMinimalWord)
            }
        }
        entranceTestViewModel.listAnswer.observe(viewLifecycleOwner) {
//            Log.d("LIST ANSWER TAG", it.toString())
        }
        binding.backwardImg.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.doneBtn.setOnClickListener {
            createEntranceResultDialog()
        }
        binding.recyclerView.adapter = adapter
        return binding.root
    }

    private fun createEntranceResultDialog() {
        val bindingDialog =
            DataBindingUtil.inflate<EntranceResultDialogBinding>(LayoutInflater.from(context),
                R.layout.entrance_result_dialog,
                null,
                false)
        val dialog = AlertDialog.Builder(context).setView(bindingDialog.root).create()
        Log.d("RESULT LEVEL TAG", entranceTestViewModel.getResultLevel())
        when (entranceTestViewModel.getResultLevel()) {
            "BEGINNER" -> bindingDialog.resultLevelTxt.setImageResource(R.drawable.level_beginner_img)
            "INTERMEDIATE" -> bindingDialog.resultLevelTxt.setImageResource(R.drawable.level_intermidiate_img)
            "ADVANCED" -> bindingDialog.resultLevelTxt.setImageResource(R.drawable.level_advanced_img)
        }
        dialog.setCancelable(false)
        bindingDialog.tryAgainBtn.setOnClickListener {
            entranceTestViewModel.getListWord()
            entranceTestViewModel.resetAnswer()
            dialog.dismiss()
        }
        bindingDialog.continueBtn.setOnClickListener {
            entranceTestViewModel.putCurrentLevel()
            findNavController().navigate(R.id.action_entranceTestFragment_to_setGoalFragment)
            dialog.dismiss()
        }
        dialog.show()
    }

}