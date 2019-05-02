package com.example.reflectit.ui.data.services

import com.google.gson.annotations.SerializedName
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

data class Widget(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("category") val category: WidgetCategory,
    @SerializedName("imgUrl") val imageUrl: String
)

data class WidgetDetails(
    @SerializedName("name") val name: String,
    @SerializedName("category") val category: String,
    @SerializedName("imgUrl") val imageUrl: String,
    @SerializedName("config") val config: List<Config>
)

data class WidgetSetup(
    @SerializedName("module") val module: String,
    @SerializedName("header") val header: String? = "",
    @SerializedName("disabled") val isDisabled: Boolean = false,
    @SerializedName("position") val position: Position = Position.TopLeft,
    @SerializedName("config") val config: List<Config> = emptyList()
)

data class Config(
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: FieldType,
    @SerializedName("description") val description: String?
)

enum class WidgetCategory {
    @SerializedName("weather") Weather,
    @SerializedName("info") Info,
    @SerializedName("time") Time
}

enum class FieldType {
    @SerializedName("input-string") InputString,
    @SerializedName("input-int") InputInt
}

enum class Position {
    @SerializedName("top-left") TopLeft,
    @SerializedName("top-center") TopCenter,
    @SerializedName("top-right") TopRight,
    @SerializedName("bottom-left") BottomLeft,
    @SerializedName("bottom-center") BottomCenter,
    @SerializedName("bottom-right") BottomRight
}

interface WidgetsService {
    @GET("widgets/all")
    fun getAllWidgetsAsync(): Deferred<Response<List<Widget>>>

    @GET("widgets/details")
    fun getWidgetDetailsAsync(@Query(value = "id") id: String): Deferred<Response<WidgetDetails>>

    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded; charset=utf-8")
    @POST("widgets/updateConfiguration")
    fun sendConfigurationAsync(@Field("configuration") configuration: String): Deferred<Response<Void>>


    companion object {
        fun create(hostname: String, token: String): WidgetsService {
            val okHttpClientBuilder = OkHttpClient.Builder()
            okHttpClientBuilder.addInterceptor {
                val original = it.request()
                val requestBuilder = original.newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                val request = requestBuilder.build()

                it.proceed(request)
            }
//            val testLocalHost = "10.0.2.2" //if you use phone use 'localhost' instead
//            val port = "5000"
//            val baseUrl = "http://$testLocalHost:$port/mirror/"
            //TODO change baseUrl
            val baseUrl = "http://$hostname/mirror/"
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(WidgetsService::class.java)
        }
    }
}