package com.example.reflectit.ui.device.available.pair

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.reflectit.ui.data.services.ParingService
import com.example.reflectit.ui.data.services.ParryData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PairDeviceRepository(val mirrorIp: String, val mirrorPort: String) {

    private val builder: Retrofit.Builder
    private val retrofit: Retrofit
    val paringService: ParingService
    var token = ""

    init {
        val baseUrl = "http:/$mirrorIp:$mirrorPort/mirror/"
        builder = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
        retrofit = builder.build()
        paringService = retrofit.create(ParingService::class.java)
    }

//    enum class PairResponse { Success, Failure }
    fun pair(code: String): LiveData<Boolean> { //TODO better change to enum result
        val result= MutableLiveData<Boolean>()
        val mirrorId = getMirrorId()
        val parryData= ParryData(code, mirrorId)

        val call = paringService.parry(parryData)
        call.enqueue(object: Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                result.postValue(false)
                Log.d("ERROR 1", "PAIR POST ERROR")
            }
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    token= response.body().toString()
                    result.postValue(true)
                    Log.d("ok", token)
                }
                else{
                    result.postValue(false)
                    Log.d("ERROR 2", "PAIR POST ERROR")
                }
            }
        })
        return result
    }

//    fun pair(code: String) {
//        val mirrorId = getMirrorId()
//        val parryData= ParryData(code, mirrorId)
//
//        var call = paringService.parry(parryData)
//        call.enqueue(object: Callback<String>{
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                Log.d("ERROR 1", "PAIR POST ERROR")
//            }
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//                if(response.isSuccessful){
//                    token= response.body().toString()
//                    Log.d("ok", token)
//                }
//                else{
//                    Log.d("ERROR 2", "PAIR POST ERROR")
//                }
//            }
//        })
//    }

    fun getMirrorId() : String{
        var mirrorId=""
        val call = paringService.getMirrorId()
        call.enqueue(object: Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("ERROR", "PAIR GET ID ERROR");
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                mirrorId=response.body().toString()
            }
        })
        return mirrorId
    }
}