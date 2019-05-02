package com.example.reflectit.ui.data.services

import com.google.gson.annotations.SerializedName
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

class ParryData(
    @SerializedName("parryingCode") val code: Int,
    @SerializedName("deviceIdentificator") val id: String
)

class MirrorIdResponse(@SerializedName("id") val id: String)
class MirrorAuthResponse(
    @SerializedName("status") val status: String,
    @SerializedName("token") val token: String
)


interface ParingService {

    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded; charset=utf-8")
    @POST("device/parry")
    fun parryAsync(
        @Field("parryingCode") parryingCode: String,
        @Field("deviceIdentificator") deviceIdentificator: String
    ): Deferred<Response<MirrorAuthResponse>>

    @GET("device/id")
    fun getMirrorIdAsync(): Deferred<Response<MirrorIdResponse>>

    companion object {
        fun create(hostname: String): ParingService {
            //val testLocalHost = "10.0.2.2" //if you use phone use 'localhost' instead
            //val port = "5000"
            //val baseUrl = "http://$testLocalHost:$port/mirror/"
            val baseUrl = "http://$hostname/mirror/"
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(ParingService::class.java)
        }
    }
}
