package com.example.youtube.utils

import android.content.Intent
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(text:String){
    Glide.with(this).load(text).into(this)
}
