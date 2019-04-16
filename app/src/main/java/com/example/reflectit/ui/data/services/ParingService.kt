package com.example.reflectit.ui.data.services

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
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
    fun parry(@Field("parryingCode") parryingCode: String,
              @Field("deviceIdentificator") deviceIdentificator: String): Call<MirrorAuthResponse>

    @GET("device/id")
    fun getMirrorId(): Call<MirrorIdResponse>
}