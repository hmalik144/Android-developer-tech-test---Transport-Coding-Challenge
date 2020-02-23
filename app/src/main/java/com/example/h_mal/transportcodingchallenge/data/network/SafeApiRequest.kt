package com.example.h_mal.transportcodingchallenge.data.network

import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

abstract class SafeApiRequest {

    //safe api call to safely return our response from the our object
    // in the form of {@RoadObject} or throw an error based off the error message in the JSON
    suspend fun<T: Any> apiRequest(call: suspend () -> Response<T>) : T{
        val response = call.invoke()
        if(response.isSuccessful){
            return response.body()!!
        }else{
            val error = response.errorBody()?.string()

            val message = StringBuilder()
            error?.let{
                try{
                    message.append(JSONObject(it).getString("message"))
                }catch(e: JSONException){ }

            }
            Log.e("Network Error","Error Code: ${response.code()}")

            throw IOException(message.toString())
        }
    }

}