package com.example.reflectit.ui.device.available.details.profiles.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.reflectit.ui.data.services.Widget
import com.example.reflectit.ui.data.services.WidgetsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileDetailsRepository {
    private val baseUrl = ""
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WidgetsService::class.java)

//    fun getAllWidgets(): LiveData<List<Widget>> {
//        val widgets = MutableLiveData<List<Widget>>()
//        retrofit.getAllWidgetsAsync().enqueue(
//            object: Callback<List<Widget>> {
//                override fun onFailure(call: Call<List<Widget>>, t: Throwable) {
//                    throw t
//                }
//
//                override fun onResponse(call: Call<List<Widget>>, response: Response<List<Widget>>) {
//                    if (response.isSuccessful) {
//                        widgets.postValue(response.body())
//                        Log.d("R", "Fetched widgets")
//                    } else {
//                        Log.d("R", "Response isn't successful")
//                        throw Throwable("Response isn't successful")
//                    }
//                }
//            }
//        )
//        return widgets
//    }
//
//    fun getWidget(id: Int): LiveData<Widget> {
//        val widget = MutableLiveData<Widget>()
//        retrofit.getWidgetByIdAsync(id.toString()).enqueue(
//            object: Callback<Widget> {
//                override fun onFailure(call: Call<Widget>, t: Throwable) {
//                    throw t
//                }
//
//                override fun onResponse(call: Call<Widget>, response: Response<Widget>) {
//                    if (response.isSuccessful) {
//                        widget.postValue(response.body())
//                        Log.d("R", "Fetched widget with id: $id")
//                    } else {
//                        Log.d("R", "Response isn't successful")
//                        throw Throwable("Response isn't successful")
//                    }
//                }
//            }
//        )
//        return widget
//    }
}