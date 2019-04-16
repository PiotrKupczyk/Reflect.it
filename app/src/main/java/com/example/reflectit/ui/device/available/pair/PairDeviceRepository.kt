package com.example.reflectit.ui.device.available.pair

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.reflectit.ui.data.services.MirrorAuthResponse
import com.example.reflectit.ui.data.services.MirrorIdResponse
import com.example.reflectit.ui.data.services.ParingService
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PairDeviceRepository(val hostname: String) {

    private val retrofit: Retrofit
    val paringService: ParingService

    init {
        /*val testLocalHost = "10.0.2.2" //if you use phone use 'localhost' instead
        val port = "5000"
        val baseUrl = "http://$testLocalHost:$port/mirror/"*/
        //Don't remove it /\/\/\/\/\

        val baseUrl = "http://$hostname/mirror/"
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        paringService = retrofit.create(ParingService::class.java)
    }

    fun pair(code: String): LiveData<String?> { //returns auth token
        val result = MutableLiveData<String?>()
        val mirrorId = getMirrorId() //TODO getMirrorId is async and we should wait for a result before we call parryingService
        val call = paringService.parry(code, "any string")
        call.enqueue(object : Callback<MirrorAuthResponse> {
            override fun onFailure(call: Call<MirrorAuthResponse>, t: Throwable) {
                result.postValue(null)
                Log.d("ERROR 1", "PAIR POST ERROR... ${t.message}")
            }

            override fun onResponse(call: Call<MirrorAuthResponse>, response: Response<MirrorAuthResponse>) {
                if (response.isSuccessful) {
                    if (response.body()?.status == "success") {
                        result.postValue(response.body()?.token)
                    }
                } else {
                    result.postValue(null)
                    Log.d("ERROR 2", "AUTH FAILED!!")
                }
            }
        })
        return result
    }

    fun getMirrorId(): String {
        var mirrorId = ""
        val call = paringService.getMirrorId()
        call.enqueue(object : Callback<MirrorIdResponse> {
            override fun onFailure(call: Call<MirrorIdResponse>, t: Throwable) {
                Log.d("ERROR", "PAIR GET ID ERROR... ${t.message}")
            }

            override fun onResponse(call: Call<MirrorIdResponse>, response: Response<MirrorIdResponse>) {
                mirrorId = response.body()?.id!!
                Log.d("ok", response.body()?.id!!)
            }

        })
        return mirrorId
    }
}