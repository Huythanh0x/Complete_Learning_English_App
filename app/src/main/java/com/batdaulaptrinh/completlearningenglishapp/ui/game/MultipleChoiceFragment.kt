package com.batdaulaptrinh.completlearningenglishapp.ui.game

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.CompleteGameDialogBinding
import com.batdaulaptrinh.completlearningenglishapp.databinding.CorrectAnswerNextDialogBinding
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentMultipleChoiceBinding
import com.batdaulaptrinh.completlearningenglishapp.databinding.IncorrectAnswerNextDialogBinding
import com.batdaulaptrinh.completlearningenglishapp.model.WordSet
import com.batdaulaptrinh.completlearningenglishapp.ui.adapter.WrongAnswerRecyclerAdapter
import com.batdaulaptrinh.completlearningenglishapp.utils.Utils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar


class MultipleChoiceFragment : Fragment() {
    companion object {
        const val KEY_AGRS_SET = "WORD_SET"
    }

    var isCorrectAnswer: Boolean = false

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
        handleQuestion("B")
        return binding.root
    }

    private fun handleQuestion(correctAnswer: String) {
        for (index in 0 until (binding.answerConstraintLayout as ViewGroup).childCount) {
            val nextChild = (binding.answerConstraintLayout as ViewGroup).getChildAt(index)
            if (nextChild.tag.toString() == "a" || nextChild.tag.toString() == "b" || nextChild.tag.toString() == "c" || nextChild.tag.toString() == "d")
                nextChild.setOnClickListener {
                    adjustMarginTopOfRoot(-550)
                    showIncorrectAnswer(it.tag.toString())
                    showCorrectAnswer(correctAnswer)
                    if (it.tag.toString().uppercase() == correctAnswer) {
                        createCorrectNextBottomSheet()
                    } else {
                        createIncorrectNextBottomSheet(correctAnswer)
                    }
                }
        }
    }

    private fun adjustMarginTopOfRoot(pixel: Int) {
        val params = FrameLayout.LayoutParams(binding.root.layoutParams)
        params.topMargin = pixel
        binding.root.layoutParams = params
    }

    private fun resetAllRing() {
        for (index in 0 until (binding.answerConstraintLayout as ViewGroup).childCount) {
            val nextChild = (binding.answerConstraintLayout as ViewGroup).getChildAt(index)
            if (nextChild.tag == "atxt" || nextChild.tag == "btxt" || nextChild.tag == "ctxt" || nextChild.tag == "dtxt") {
                nextChild.setBackgroundColor(Color.WHITE)
            }
        }
    }

    private fun showCorrectAnswer(answerCode: String) {
        for (index in 0 until (binding.answerConstraintLayout as ViewGroup).childCount) {
            val nextChild = (binding.answerConstraintLayout as ViewGroup).getChildAt(index)
            if (nextChild.tag == answerCode.lowercase() + "txt") {
                isCorrectAnswer = true
                nextChild.background =
                    AppCompatResources.getDrawable(requireContext(), R.drawable.correct_answer_bg)
            } else {
                isCorrectAnswer = false
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

    private fun createCorrectNextBottomSheet() {
        val dialogBinding = DataBindingUtil.inflate<CorrectAnswerNextDialogBinding>(layoutInflater,
            R.layout.correct_answer_next_dialog,
            null,
            false)
        val dialog = BottomSheetDialog(requireContext())
        dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog.setCancelable(false)
        dialog.setContentView(dialogBinding.root)
        dialogBinding.nextBtn.setOnClickListener {
            dialog.dismiss()
            resetAllRing()
            adjustMarginTopOfRoot(0)
            createCompleteDialog()
        }
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    private fun createIncorrectNextBottomSheet(correctAnswer: String) {
        val dialogBinding =
            DataBindingUtil.inflate<IncorrectAnswerNextDialogBinding>(layoutInflater,
                R.layout.incorrect_answer_next_dialog,
                null,
                false)
        val dialog = BottomSheetDialog(requireContext())
        dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog.setCancelable(false)
        dialogBinding.correctAnswerTxt.text = correctAnswer
        dialog.setContentView(dialogBinding.root)
        dialogBinding.nextBtn.setOnClickListener {
            dialog.dismiss()
            resetAllRing()
            createCompleteDialog()
            adjustMarginTopOfRoot(0)
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
        dialog.window?.setDimAmount(0.5f)
        dialog.setCancelable(false)
        dialogBinding.listWrongAnswerRv.adapter = WrongAnswerRecyclerAdapter(arrayListOf())
        dialogBinding.tryAgainGameCardBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.addToNextSetBtn.setOnClickListener {
            //TODO("back ground and button")
            Snackbar.make(dialogBinding.root,
                "Wrong words was added to next set",
                Snackbar.LENGTH_SHORT).setAction("Undo") {
            }.show()
        }
        dialogBinding.backToMenuFlashCardBtn.setOnClickListener {
            dialog.dismiss()
            findNavController().popBackStack()
        }
        dialog.show()
    }
}