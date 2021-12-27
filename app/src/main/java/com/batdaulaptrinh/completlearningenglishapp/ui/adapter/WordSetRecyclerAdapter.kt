package com.batdaulaptrinh.completlearningenglishapp.ui.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.data.sharedPreferences.SharePreferencesProvider
import com.batdaulaptrinh.completlearningenglishapp.databinding.WordSetRowBinding
import com.batdaulaptrinh.completlearningenglishapp.model.WordSet
import timber.log.Timber
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import kotlin.random.Random


class WordSetRecyclerAdapter(
    private val listWordSet: ArrayList<WordSet>,
    private val clickListener: (wordSet: WordSet) -> Unit
) :
    RecyclerView.Adapter<WordSetRecyclerAdapter.MyViewHolder>() {
    class MyViewHolder(
        val binding: WordSetRowBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(
            wordSet: WordSet,
            position: Int
        ) {
            val currentSet = SharePreferencesProvider(binding.root.context).getCurrentSetNth()
            if (position >= currentSet) {
                binding.thumbnailImg.foreground = AppCompatResources.getDrawable(
                    binding.root.context,
                    R.drawable.lock_chain_set_word
                )
                binding.thumbnailImg.setImageResource(R.drawable.thumbnail_locked_set)
                val progress = 0
                binding.progressPb.progress = progress
                "$progress/20".also { binding.progressTxt.text = it }
            } else {
                val decodedString: ByteArray = Base64.decode(wordSet.imageOfSet, Base64.DEFAULT)
                val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                binding.thumbnailImg.setImageBitmap(decodedByte)
                binding.thumbnailImg.foreground = null
                val progress = Random.nextInt(20)
                binding.progressPb.progress = progress
                "$progress/20".also { binding.progressTxt.text = it }
            }
            binding.wordSetNumberTxt.text = "Word set number ${wordSet.setNth + 1}"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = DataBindingUtil.inflate<WordSetRowBinding>(
            LayoutInflater.from(parent.context),
            R.layout.word_set_row, parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentSet = SharePreferencesProvider(holder.binding.root.context).getCurrentSetNth()
        Timber.d("Current position $position")
        holder.bind(listWordSet[position], position)
        holder.binding.root.setOnClickListener {
            if (position >= currentSet) {
                MotionToast.createColorToast(
                    holder.binding.root.context as Activity,
                    "WARNING",
                    "Require complete previous set",
                    MotionToastStyle.WARNING,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.SHORT_DURATION,
                    ResourcesCompat.getFont(
                        holder.binding.root.context as Activity,
                        R.font.helvetica_regular
                    )
                )
            } else
                clickListener(listWordSet[position])
        }
    }

    override fun getItemCount() = listWordSet.size

}
