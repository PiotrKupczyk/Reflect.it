package com.example.reflectit.ui.device.available.details.widgets

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.example.reflectit.ui.data.services.*
import com.example.reflectit.ui.extensions.appendAsync
import kotlinx.coroutines.*

//class WidgetPosition(val widget: Widget, val position: Position? = null)

class SharedWidgetsSelectorViewModel(private val repository: WidgetsRepository) : ViewModel() {
    val selectedWidgets = MutableLiveData<ArrayList<Widget>>().apply { postValue(arrayListOf()) }



    fun selectWidget(element: Widget) {
        selectedWidgets.appendAsync(element)
    }

    fun getWidgetBy(id: String): LiveData<WidgetDetails> {
        val result = MutableLiveData<WidgetDetails>()

        CoroutineScope(Dispatchers.Default).launch {
            val widgetDetails = repository.getWidgetDetails(id)
            if (widgetDetails != null)
                result.postValue(widgetDetails)
        }

        return result
    }

    fun getAllWidgets(): LiveData<ArrayList<Widget>> {
        val result = MutableLiveData<ArrayList<Widget>>()

        CoroutineScope(Dispatchers.Default).launch {
            val widgets = repository.getAllWidgets()
            if (widgets != null) {
                result.postValue(ArrayList(widgets))
            }
        }

        return result
    }

    fun updateCurrentConfiguration() {
        if (selectedWidgets.value != null)  {
            val configurations = selectedWidgets
                .value
                ?.mapIndexed { index, widget ->
                    if (widget.category != WidgetCategory.Placeholder) {
                        index
                    } else
                        -1 }
                ?.filter {it != -1}
                ?.map {
                    val configurations = mutableListOf<Config>()
                    val widget = selectedWidgets.value!![it]
                    if (widget.category == WidgetCategory.Weather) { // just for now
//                        Config()
//                        configurations.add()
                    }
                    val position = Position.getPositionByIndex(it) ?: Position.TopLeft
//                    val position = Position.TopLeft
                    WidgetSetup(widget.name, position = position)
                }
            if (configurations != null) {
                CoroutineScope(Dispatchers.Default).launch {
                    val code = repository.updateConfiguration(configurations)
                    Log.d("WIDGET SERVICE", code.toString())
                }
            } else return
        } else return
        //TODO send correct configuration
    }
}
