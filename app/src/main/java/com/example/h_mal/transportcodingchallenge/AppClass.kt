package com.example.h_mal.transportcodingchallenge

import android.app.Application
import com.example.h_mal.transportcodingchallenge.data.network.ApiData
import com.example.h_mal.transportcodingchallenge.data.network.NetworkConnectionInterceptor
import com.example.h_mal.transportcodingchallenge.data.repositories.Repository
import com.example.h_mal.transportcodingchallenge.ui.main.MainViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class AppClass :Application(), KodeinAware{

    //kodeinaware for dependency injection
    override val kodein =  Kodein.lazy {
        //import relevent module
        import(androidXModule(this@AppClass))

        //create instance of network interceptor
        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        //create an instance of Api data class for retrofit calls
        bind() from singleton { ApiData(instance()) }
        //create an instance of the repository with above api class inserted
        bind() from singleton { Repository(instance(), instance()) }
        //create an instance of viewmodel factory with above repository inserted
        bind() from provider{ MainViewModelFactory(instance())}
    }
}