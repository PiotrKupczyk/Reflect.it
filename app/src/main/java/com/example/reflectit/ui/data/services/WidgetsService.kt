package com.example.reflectit.ui.data.services

import com.google.gson.annotations.SerializedName
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

data class Widget(@SerializedName("id") val id: Int,
                  @SerializedName("name") val name: String,
                  @SerializedName("category") val category: String,
                  @SerializedName("imgUrl") val imageUrl: String
//                  @SerializedName("config") val config: Config
)

data class Configuration(@SerializedName("module") val module: String,
                         @SerializedName("position") val position: String?
//                         @SerializedName("config") val config: Config
                         )

//data class Config(@SerializedName("name") val name: String,
//                  @SerializedName("type") val type: FieldType,
//                  @SerializedName("description") val description: String,
//                  @SerializedName("details") val details: Map<String, Any>
//            )

enum class WidgetCategory {
    weather,
    info,
    time
}

interface WidgetsService {
    @GET("widgets/all")
    fun getAllWidgetsAsync(@Header("Authorization") token: String): Deferred<Response<List<Widget>>>

    @GET("widgets/details")
    fun getWidgetByIdAsync(@Query(value = "id") id: String): Deferred<Response<Widget>>

    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded; charset=utf-8")
    @POST("widgets/updateConfiguration")
    fun sendConfigurationAsync(@Field("configuration") configuration: String): Deferred<Response<Void>>
//    fun sendConfigurationAsync(@Field("configuration") configuration: List<Configuration>): Deferred<Response<Void>>


    companion object {
        fun create(hostname: String): WidgetsService {
//            val testLocalHost = "10.0.2.2" //if you use phone use 'localhost' instead
//            val port = "5000"
//            val baseUrl = "http://$testLocalHost:$port/mirror/"
            val baseUrl = "http://$hostname/mirror/"
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(WidgetsService::class.java)
        }
    }
}