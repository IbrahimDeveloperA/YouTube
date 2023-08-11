package com.example.youtube.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE

class Pref(context: Context) {

    private val pref = context.getSharedPreferences("prefs",MODE_PRIVATE)

    var onBord: Boolean
        get() = pref.getBoolean("prefs",false)
        set(value) {
            pref.edit().putBoolean("prefs",value).apply()
        }
}