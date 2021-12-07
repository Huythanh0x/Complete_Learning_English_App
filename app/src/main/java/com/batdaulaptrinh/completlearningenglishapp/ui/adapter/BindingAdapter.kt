package com.batdaulaptrinh.completlearningenglishapp.ui.adapter

import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.model.MinimalWord
import com.batdaulaptrinh.completlearningenglishapp.model.Word
import com.batdaulaptrinh.completlearningenglishapp.utils.Utils
import com.bumptech.glide.Glide

@BindingAdapter("setImage")
fun setImage(imageView: ImageView, word: Word?) {
    try {
        val decodedString: ByteArray = Base64.decode(word?.thumbnail, Base64.DEFAULT)
        val bitmap =
            BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        Glide.with(imageView)
            .load(bitmap) // image url
            .placeholder(R.drawable.app_logo_img) // any placeholder to load at start
            .error(R.drawable.app_logo_img)  // any image in case of error
            .centerCrop()
            .into(imageView)  // imageview object
    } catch (e: Exception) {
        Log.e("LOAD IMAGE", e.toString())
    }
}

@BindingAdapter("setStar")
fun setStar(imageView: ImageView, word: Word?) {
    if (word != null) {
        if (word.is_favourite == 1) {
            imageView.setImageResource(R.drawable.ic_baseline_star_24)
        } else {
            imageView.setImageResource(R.drawable.ic_baseline_star_border_24)
        }
    }
}

@BindingAdapter("playSoundUs")
fun playSoundUs(imageView: ImageView, word: Word?) {
    imageView.setOnClickListener {
        word?.let { it1 -> Utils.playSound(it1.mp3_us) }
    }

}

@BindingAdapter("setStarMinimal")
fun setStarMinimal(imageView: ImageView, minimalWord: MinimalWord?) {
    if (minimalWord != null) {
        if (minimalWord.is_favourite == 1) {
            imageView.setImageResource(R.drawable.ic_baseline_star_24)
        } else {
            imageView.setImageResource(R.drawable.ic_baseline_star_border_24)
        }
    }
}

@BindingAdapter("playSoundUsMinimal")
fun playSoundUsMinimal(imageView: ImageView, minimalWord: MinimalWord?) {
    imageView.setOnClickListener {
        minimalWord?.let { it1 -> Utils.playSound(it1.mp3_us) }
    }

}

@BindingAdapter("playSoundUK")
fun playSoundUK(imageView: ImageView, word: Word?) {
    imageView.setOnClickListener {
        word?.let { it1 -> Utils.playSound(it1.mp3_us) }
    }

}

@BindingAdapter("setStatePlayImage")
fun setStatePlayImage(imageView: ImageView, isAutoPlay: LiveData<Boolean>) {
    imageView.setOnClickListener {
        if (isAutoPlay.value == true) {
            imageView.setImageResource(R.drawable.pause_flashcar_ic)
        } else if (isAutoPlay.value == false) {
            imageView.setImageResource(R.drawable.play_flash_card_ic)
        }
    }
}

