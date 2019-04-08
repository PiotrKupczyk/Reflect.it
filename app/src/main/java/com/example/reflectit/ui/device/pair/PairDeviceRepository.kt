package com.example.reflectit.ui.device.pair

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.reflectit.ui.data.services.MirrorApi
import com.example.reflectit.ui.data.services.ParryData
import kotlinx.android.synthetic.main.pair_device_fragment.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.log

class PairDeviceRepository(val mirrorIp: String, val mirrorPort: String) {

    val builder: Retrofit.Builder
    val retrofit: Retrofit
    val mirrorApi: MirrorApi
    var token = ""

    init {
        val base = "http:/$mirrorIp:$mirrorPort/mirror/"
        builder = Retrofit.Builder().baseUrl(base).addConverterFactory(GsonConverterFactory.create())
        retrofit = builder.build()
        mirrorApi = retrofit.create(MirrorApi::class.java)
    }

    fun pair(code: String) : LiveData<Boolean> {
        val result= MutableLiveData<Boolean>()
        val mirrorId = getMirrorId()
        val parryData= ParryData(code, mirrorId)

        var call = mirrorApi.parry(parryData)
        call.enqueue(object: Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("ERROR 1", "PAIR POST ERROR")
            }
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    token= response.body().toString()
                    Log.d("ok", token)
                }
                else{
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
//        var call = mirrorApi.parry(parryData)
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
        var call = mirrorApi.getMirrorId()
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