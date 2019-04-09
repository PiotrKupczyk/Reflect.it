package com.example.reflectit.ui.data.services

import com.google.gson.annotations.SerializedName
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header

class ParryData(@SerializedName("parryCode") val code: String,
                   @SerializedName("deviceIdentyficator") val id: String)

interface ParingService {
    @POST("device/parry")
    fun parry (@Body parryDetails: ParryData) : Call<String>

    @GET("device")
    fun getMirrorId() : Call<String>
}