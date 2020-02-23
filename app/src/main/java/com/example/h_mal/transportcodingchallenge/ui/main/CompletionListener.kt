package com.example.h_mal.transportcodingchallenge.ui.main

interface CompletionListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
}