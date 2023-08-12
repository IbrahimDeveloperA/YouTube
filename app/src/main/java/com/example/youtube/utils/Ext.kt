package com.example.youtube.utils

import android.content.Intent
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.youtube.ui.details.DetailActivity

fun ImageView.loadImage(text:String){
    Glide.with(this).load(text).into(this)
}

fun AppCompatActivity.replaceActivity(destinationActivity: Class<*>){
    val intent = Intent(this, destinationActivity::class.java)
    startActivity(intent)
    finish()
}