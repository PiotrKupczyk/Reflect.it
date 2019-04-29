package com.example.reflectit.ui.device.available.details.widgets

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.example.reflectit.ui.data.models.RemoteWidget
import com.example.reflectit.ui.data.services.Configuration
import com.example.reflectit.ui.data.services.Widget
import com.example.reflectit.ui.extensions.appendAsync
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.internal.GsonBuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.converter.gson.GsonConverterFactory

class SharedWidgetsSelectorViewModel(val repository: WidgetsRepository) : ViewModel() {
    val selectedWidgets = MutableLiveData<ArrayList<Widget>>()

    fun selectWidget(widget: Widget) {
        selectedWidgets.appendAsync(widget)
    }

    fun loadWidgets(): LiveData<ArrayList<Widget>> {
        val result = MutableLiveData<ArrayList<Widget>>()
        CoroutineScope(Dispatchers.Main).launch {
             val widgets = repository.getAllWidgets()
             if (widgets != null) {
                 result.postValue(ArrayList(widgets))
             }
        }
        return result
    }

    fun updateCurrentConfiguration() {
        CoroutineScope(Dispatchers.Default).launch {
            val configurations = mutableListOf<Configuration>()
            val positions = listOf("top_left, bottom_left, top_right, bottom_right")
            selectedWidgets.value?.forEach { widget ->
                configurations.add(Configuration(widget.name, positions.random()))
            }
            val code = repository.sendConfiguration(configurations)
        }
    }
}
