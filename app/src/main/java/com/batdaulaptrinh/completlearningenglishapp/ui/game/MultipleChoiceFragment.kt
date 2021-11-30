package com.batdaulaptrinh.completlearningenglishapp.ui.game

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.CompleteGameDialogBinding
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentMultipleChoiceBinding
import com.batdaulaptrinh.completlearningenglishapp.databinding.PureNextDialogBinding
import com.batdaulaptrinh.completlearningenglishapp.model.WordSet
import com.batdaulaptrinh.completlearningenglishapp.ui.adapter.WrongAnswerRecyclerAdapter
import com.batdaulaptrinh.completlearningenglishapp.utils.Utils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar

class MultipleChoiceFragment : Fragment() {
    companion object {
        const val KEY_AGRS_SET = "WORD_SET"
    }

    lateinit var binding: FragmentMultipleChoiceBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentMultipleChoiceBinding>(layoutInflater,
            R.layout.fragment_multiple_choice,
            container,
            false)

        arguments?.let {
            val setWord = it.get(KEY_AGRS_SET)
            if (setWord is WordSet) {
                binding.titleToolBar.text = setWord.setNth.toString()
            }
        }

        binding.backwardImg.setOnClickListener {
            findNavController().popBackStack()
        }
        val answerCode = "b"
        for (index in 0 until (binding.answerConstraintLayout as ViewGroup).childCount) {
            val nextChild = (binding.answerConstraintLayout as ViewGroup).getChildAt(index)
            nextChild.setOnClickListener {
                createNextDialog()
                showCorrectAnswer(answerCode)
                showIncorrectAnswer(it.tag.toString())
            }
        }

        return binding.root
    }

    fun resetAllRing() {
        for (index in 0 until (binding.answerConstraintLayout as ViewGroup).childCount) {
            val nextChild = (binding.answerConstraintLayout as ViewGroup).getChildAt(index)
            if (nextChild.tag == "atxt"||nextChild.tag == "btxt"||nextChild.tag == "ctxt"||nextChild.tag == "dtxt") {
                nextChild.setBackgroundColor(Color.WHITE)
            }
        }
    }

    private fun showCorrectAnswer(answerCode: String) {
        for (index in 0 until (binding.answerConstraintLayout as ViewGroup).childCount) {
            val nextChild = (binding.answerConstraintLayout as ViewGroup).getChildAt(index)
            if (nextChild.tag == answerCode + "txt") {
                nextChild.background =
                    AppCompatResources.getDrawable(requireContext(), R.drawable.correct_answer_bg)
            }
        }
    }

    private fun showIncorrectAnswer(answerCode: String) {
        for (index in 0 until (binding.answerConstraintLayout as ViewGroup).childCount) {
            val nextChild = (binding.answerConstraintLayout as ViewGroup).getChildAt(index)
            if (nextChild.tag == answerCode + "txt") {
                nextChild.background =
                    AppCompatResources.getDrawable(requireContext(), R.drawable.incorrect_answer_bg)
            }
        }
    }

    private fun createNextDialog() {
        val dialogBinding = DataBindingUtil.inflate<PureNextDialogBinding>(layoutInflater,
            R.layout.pure_next_dialog,
            null,
            false)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setCancelable(false)
        dialog.setContentView(dialogBinding.root)
        dialogBinding.nextBtn.setOnClickListener {
            dialog.dismiss()
            createCompleteDialog()
        }
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    private fun createCompleteDialog() {
        val dialogBinding =
            DataBindingUtil.inflate<CompleteGameDialogBinding>(LayoutInflater.from(requireContext()),
                R.layout.complete_game_dialog,
                null,
                false)
        val dialog = AlertDialog.Builder(context).setView(dialogBinding.root).create()
        dialog.setCancelable(false)
        dialogBinding.listWrongAnswerRv.adapter = WrongAnswerRecyclerAdapter(Utils.getWordList())
        dialogBinding.tryAgainGameCardBtn.setOnClickListener {
            resetAllRing()
            dialog.dismiss()
        }
        dialogBinding.addToNextSetBtn.setOnClickListener {
            dialog.dismiss()
            Snackbar.make(binding.root,
                "Wrong words was added to next set",
                Snackbar.LENGTH_LONG).setAction("Undo") {
            }.show()
            findNavController().popBackStack()
        }
        dialogBinding.backToMenuFlashCardBtn.setOnClickListener {
            dialog.dismiss()
            findNavController().popBackStack()
        }
        dialog.show()
    }
}