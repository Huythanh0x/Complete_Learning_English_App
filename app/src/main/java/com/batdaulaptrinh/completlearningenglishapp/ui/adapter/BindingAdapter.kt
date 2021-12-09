package com.batdaulaptrinh.completlearningenglishapp.ui.adapter

import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.model.MinimalWord
import com.batdaulaptrinh.completlearningenglishapp.model.Word
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.URL
import java.net.URLConnection

val mediaPlayer = MediaPlayer()

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
        word?.let { it1 -> playSound(it1.mp3_us) }
    }

}

@BindingAdapter(value = ["word", "preferAccent"])
fun playSoundFromXML(imageView: ImageView, word: Word?, preferAccent: String) {
    if (preferAccent == "US") {
        imageView.setImageResource(R.drawable.us_speaker_ic)
    } else {
        imageView.setImageResource(R.drawable.uk_speaker_ic)
    }
    imageView.setOnClickListener {
        if (word != null) {
            if (preferAccent == "US")
                playSound(word.mp3_us)
            else
                playSound(word.mp3_uk)
        }
    }
}

@BindingAdapter(value = ["minimalWord", "preferAccent"])
fun playSoundMinimalWordFromXML(
    imageView: ImageView,
    minimalWord: MinimalWord?,
    preferAccent: String,
) {
    if (preferAccent == "US") {
        imageView.setImageResource(R.drawable.us_speaker_ic)
    } else {
        imageView.setImageResource(R.drawable.uk_speaker_ic)
    }
    imageView.setOnClickListener {
        if (minimalWord != null) {
            if (preferAccent == "US")
                playSound(minimalWord.mp3_us)
            else
                playSound(minimalWord.mp3_uk)
        }
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
        minimalWord?.let { it1 -> playSound(it1.mp3_us) }
    }

}

@BindingAdapter("playSoundUK")
fun playSoundUK(imageView: ImageView, word: Word?) {
    imageView.setOnClickListener {
        word?.let { it1 -> playSound(it1.mp3_uk) }
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

@BindingAdapter("example")
fun setExampleText(textView: TextView, word: Word?) {
    if (word != null) {
        var convertedText = word.examples.replace(";", "\n▪ ")
        convertedText = "▪ $convertedText"
        textView.text = convertedText
    }
}

fun playSound(mp3Us: String) {
    try {
        GlobalScope.launch(Dispatchers.IO) {
            val base64String = getByteArrayFromImageURL(mp3Us)
            if (base64String != null) {
                playAudio(base64String)
            }
        }
    } catch (e: Exception) {
        Log.e("response", e.toString())
    }
}


private fun getByteArrayFromImageURL(url: String): String? {
    try {
        val imageUrl = URL(url)
        val urlConnection: URLConnection = imageUrl.openConnection()
        val inputStream: InputStream = urlConnection.getInputStream()
        val bytesOutputStream = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var read = 0
        while (inputStream.read(buffer, 0, buffer.size).also { read = it } != -1) {
            bytesOutputStream.write(buffer, 0, read)
        }
        bytesOutputStream.flush()
        return Base64.encodeToString(bytesOutputStream.toByteArray(), Base64.DEFAULT)
            .filter { !it.isWhitespace() }
    } catch (e: Exception) {
        Log.d("Error", e.toString())
    }
    return null
}

private fun playAudio(base64EncodedString: String) {
    try {
        val url = "data:audio/mp3;base64,$base64EncodedString"
        mediaPlayer.reset()
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepare()
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener { mediaPlayer ->
            mediaPlayer.reset()
        }
    } catch (ex: Exception) {
        Log.e("TAG ERROR PLAYER", ex.toString())
    }
}
