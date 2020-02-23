package com.example.h_mal.transportcodingchallenge.data.repositories

import android.content.Context
import com.example.h_mal.transportcodingchallenge.R
import com.example.h_mal.transportcodingchallenge.data.model.RoadData
import com.example.h_mal.transportcodingchallenge.data.network.ApiData
import com.example.h_mal.transportcodingchallenge.data.network.SafeApiRequest

class Repository(
    private val api: ApiData,
    context: Context
): SafeApiRequest(){

    //retrieve the values from the resourse class
    private val apiKey: String = context.getString(R.string.Api_key)
    private val appId: String = context.getString(R.string.Application_ID)

    //api function to retrieve data from the api via retrofit
    //return the data in the for of list as that is how the JSON is
    suspend fun getRoadData(roadId: String): List<RoadData>?{
        return apiRequest {
            api.getRoadData(roadId, apiKey, appId)
        }
    }
}