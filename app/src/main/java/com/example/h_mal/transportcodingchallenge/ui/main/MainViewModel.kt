package com.example.h_mal.transportcodingchallenge.ui.main

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.h_mal.transportcodingchallenge.data.model.RoadData
import com.example.h_mal.transportcodingchallenge.data.repositories.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel(
    private val repository: Repository
) : ViewModel() {

    //completion listener
    var completionListener: CompletionListener? = null

    //livedata to populate views in the UI
    var roadLiveDate: MutableLiveData<RoadData> = MutableLiveData()

    //databinding the text in the edittext in the layout
    var roadId: String? = null

    //databinding the onclick of the submit button in the view
    fun submit(view: View?){
        //callback to the view that the operation has started
        completionListener?.onStarted()

        //null check the input
        if (roadId.isNullOrEmpty()){
            completionListener?.onFailure("No Road ID inserted")
            return
        }

        //launch a coroutine on main thread to fetch data
        CoroutineScope(Dispatchers.Main).launch {
            try {
                //fetch data from the repository
                val response = repository.getRoadData(roadId!!.trim())

                //unwrap if response is not null
                response?.get(0)?.let {
                    //update the live data
                    roadLiveDate.value = it
                    //callback to ciew that operation was successful
                    completionListener?.onSuccess()
                    return@launch
                }
                //callback operation was failed
                completionListener?.onFailure("Failed to retrieve data")
            }catch (e: IOException){
                //callback operation was failed
                completionListener?.onFailure(e.message!!)
            }
        }
    }
}