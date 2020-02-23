package com.example.h_mal.transportcodingchallenge.data.network

import com.example.h_mal.transportcodingchallenge.data.model.RoadData

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiData {


    //Retrofit api call with path being the road id we input
    @GET("{roadId}")
    suspend fun getRoadData(@Path("roadId") roadId: String,
                            @Query("app_key") app_key: String,
                            @Query("app_id") app_id: String) : Response<List<RoadData>>

    companion object{
        //class invokation with the network interceptor passed
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ) : ApiData{

            //build okhttpclient
            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            //return our API data class
            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl("https://api.tfl.gov.uk/Road/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiData::class.java)
        }
    }

}