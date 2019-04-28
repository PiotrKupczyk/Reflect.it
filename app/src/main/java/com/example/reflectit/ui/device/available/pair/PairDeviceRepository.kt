package com.example.reflectit.ui.device.available.pair

import com.example.reflectit.ui.data.services.ParingService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PairDeviceRepository(private val hostname: String) {

    private val retrofit: Retrofit
    private val paringService: ParingService

    init {
        val testLocalHost = "10.0.2.2" //if you use phone use 'localhost' instead
        val port = "5000"
        val baseUrl = "http://$testLocalHost:$port/mirror/"
        //Don't remove it /\/\/\/\/\

//        val baseUrl = "http://$hostname/mirror/"
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        paringService = retrofit.create(ParingService::class.java)
    }

    suspend fun pair(code: String): String? { //returns auth token
        val deviceId = "any string just for now"
        val request = paringService.parryAsync(code, deviceId)
            val response = request.await()
            if (response.isSuccessful) {
                return response.body()?.token
            }
        return null
    }

    suspend fun getMirrorId(): String? {
        var mirrorId = ""
        val request = paringService.getMirrorIdAsync()

        return runBlocking {
            val response = request.await()
            return@runBlocking if (response.isSuccessful) {
                response.body()?.id!!
            } else
                null
        }
    }


//        call.enqueue(object : Callback<MirrorIdResponse> {
//            override fun onFailure(call: Call<MirrorIdResponse>, t: Throwable) {
//                Log.d("ERROR", "PAIR GET ID ERROR... ${t.message}")
//            }
//
//            override fun onResponse(call: Call<MirrorIdResponse>, response: Response<MirrorIdResponse>) {
//                mirrorId = response.body()?.id!!
//                Log.d("ok", response.body()?.id!!)
//            }
//
//        })
//        return mirrorId
}