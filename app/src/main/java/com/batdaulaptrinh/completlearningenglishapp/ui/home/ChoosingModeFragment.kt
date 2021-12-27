package com.batdaulaptrinh.completlearningenglishapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.data.sharedPreferences.SharePreferencesProvider
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentChoosingModeBinding
import com.batdaulaptrinh.completlearningenglishapp.model.WordSet
import com.batdaulaptrinh.completlearningenglishapp.ui.home.tab.WordDayTabFragment
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class ChoosingModeFragment : Fragment() {
    companion object {
        val KEY_ARGS_SET = "KEY_ARGS"
    }

    lateinit var binding: FragmentChoosingModeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_choosing_mode, container, false)

        arguments?.let {
            val setWord = it.get(WordDayTabFragment.KEY_AGRS_SET)
            if (setWord is WordSet) {
                binding.titleToolBar.text = "WORD SET NUMBER ${setWord.setNth}"
                if (setWord.setNth == 1) {
                    binding.testCv.alpha = 0.5f
                    binding.testCv.setOnClickListener {
                        MotionToast.createColorToast(
                            requireActivity(),
                            "WARNING",
                            "There are nothing to test",
                            MotionToastStyle.WARNING,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(requireActivity(), R.font.helvetica_regular)
                        )
                    }
                    binding.wordListCv.setOnClickListener {
                        findNavController().navigate(
                            R.id.action_choosingModeFragment_to_wordListFragment,
                            bundleOf(KEY_ARGS_SET to setWord)
                        )
                    }
                    binding.gameCv.setOnClickListener {
                        findNavController().navigate(
                            R.id.action_choosingModeFragment_to_connectWordFragment,
                            bundleOf(KEY_ARGS_SET to setWord)
                        )
                    }
                    binding.flashCardCv.setOnClickListener {
                        findNavController().navigate(
                            R.id.action_choosingModeFragment_to_flashCardFragment,
                            bundleOf(KEY_ARGS_SET to setWord)
                        )
                    }
                } else if (setWord.setNth == SharePreferencesProvider(requireContext()).getCurrentSetNth()) {
                    binding.flashCardCv.alpha = 0.5f
                    binding.gameCv.alpha = 0.5f
                    binding.wordListCv.alpha = 0.5f
                    binding.testCv.setOnClickListener {
                        findNavController().navigate(
                            R.id.action_choosingModeFragment_to_multipleChoiceFragment,
                            bundleOf(KEY_ARGS_SET to setWord)
                        )

                    }
                    binding.wordListCv.setOnClickListener {
                        MotionToast.createColorToast(
                            requireActivity(),
                            "WARNING",
                            "Complete test to learn new word",
                            MotionToastStyle.WARNING,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(requireActivity(), R.font.helvetica_regular)
                        )
                    }
                    binding.gameCv.setOnClickListener {
                        MotionToast.createColorToast(
                            requireActivity(),
                            "WARNING",
                            "Complete test to learn new word",
                            MotionToastStyle.WARNING,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(requireActivity(), R.font.helvetica_regular)
                        )
                    }
                    binding.flashCardCv.setOnClickListener {
                        MotionToast.createColorToast(
                            requireActivity(),
                            "WARNING",
                            "Complete test to learn new word",
                            MotionToastStyle.WARNING,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(requireActivity(), R.font.helvetica_regular)
                        )
                    }
                } else {
                    binding.testCv.setOnClickListener {
                        findNavController().navigate(
                            R.id.action_choosingModeFragment_to_multipleChoiceFragment,
                            bundleOf(KEY_ARGS_SET to setWord)
                        )

                    }
                    binding.wordListCv.setOnClickListener {
                        findNavController().navigate(
                            R.id.action_choosingModeFragment_to_wordListFragment,
                            bundleOf(KEY_ARGS_SET to setWord)
                        )
                    }
                    binding.gameCv.setOnClickListener {
                        findNavController().navigate(
                            R.id.action_choosingModeFragment_to_connectWordFragment,
                            bundleOf(KEY_ARGS_SET to setWord)
                        )
                    }
                    binding.flashCardCv.setOnClickListener {
                        findNavController().navigate(
                            R.id.action_choosingModeFragment_to_flashCardFragment,
                            bundleOf(KEY_ARGS_SET to setWord)
                        )
                    }
                }
                binding.backwardImg.setOnClickListener { backBtn ->
                    val animation = AnimationUtils.loadAnimation(context, R.anim.press_view_alpla)
                    backBtn.startAnimation(animation)
                    findNavController().popBackStack()
                }
            }

        }

        return binding.root

    }


}