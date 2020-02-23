package com.example.h_mal.transportcodingchallenge.utils

import android.view.View

fun View.show(){
    visibility = View.VISIBLE
}

fun View.hide(){
    visibility = View.GONE
}

fun View.disable(){
    isEnabled = false
}

fun View.enable(){
    isEnabled = true
}