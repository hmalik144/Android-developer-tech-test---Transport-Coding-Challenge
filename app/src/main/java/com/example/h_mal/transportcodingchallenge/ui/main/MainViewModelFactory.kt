package com.example.h_mal.transportcodingchallenge.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.h_mal.transportcodingchallenge.data.repositories.Repository

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory (
    private val repository: Repository
): ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}