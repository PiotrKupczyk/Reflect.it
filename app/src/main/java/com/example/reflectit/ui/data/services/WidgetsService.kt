package com.example.reflectit.ui.data.services

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

data class Widget(@SerializedName("id") val id: Int,
                  @SerializedName("name") val name: String,
                  @SerializedName("category") val category: String,
                  @SerializedName("imgUrl") val imageUrl: String,
                  @SerializedName("config") val config: Config)

data class Config(@SerializedName("name") val name: String,
                  @SerializedName("type") val type: FieldType,
                  @SerializedName("description") val description: String,
                  @SerializedName("details") val details: Map<String, Any>
)

enum class FieldType(type: String) {
    GoogleApiKey("googleApiKey"),
    InputText("input-text"),
    InputPassword("input-password")
}


interface WidgetsService {
    @GET("mirror/widgets/all")
    fun getAllWidgets(): Call<List<Widget>> //TODO not sure if there shouldn't be Array<Widget>

    @GET("mirror/widgets/details")
    fun getWidgetById(@Query(value = "id") id: String): Call<Widget>

}