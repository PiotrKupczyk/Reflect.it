package com.example.reflectit.ui.device.available.details.widgets

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.example.reflectit.ui.data.services.*
import com.example.reflectit.ui.extensions.appendAsync
import kotlinx.coroutines.*
import java.io.Serializable

//class WidgetPosition(val widget: Widget, val position: Position? = null)

class  SharedWidgetsSelectorViewModel(private val repository: WidgetsRepository) : ViewModel() {
    val selectedWidgets = MutableLiveData<ArrayList<Widget>>()
        .apply { postValue(
            (0..11).map {
                Widget(Position.values().size, "empty", WidgetCategory.Placeholder, "") } as ArrayList<Widget>?)
         }
    val horizontalGridsPositions = listOf(0, 4, 5, 6, 10)

    fun selectWidget(element: Widget) {
        val currentWidgets = selectedWidgets.value

        val firstIndexToReplace = currentWidgets?.indexOfFirst { it.category == WidgetCategory.Placeholder }
        if (firstIndexToReplace != null && firstIndexToReplace!=-1) {
            currentWidgets[firstIndexToReplace] = element
        } else
            currentWidgets?.add(element)
        selectedWidgets.postValue(currentWidgets)
//        selectedWidgets.appendAsync(element)
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

        CoroutineScope(Dispatchers.IO).launch {
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
                    val config = mutableMapOf<String, Any>()
                    val widget = selectedWidgets.value!![it]
                    val position = Position.getPositionByIndex(it) ?: Position.TopLeft
                    when (widget.name) {
                        "weatherforecast" -> {
                            config["location"] = "Wrocław,PL"
                            config["locationID"] = "3081368"
                            config["appid"] = "b81b05eb2425dcea5e92cadc30df5721"
                            config["colorIcon"] = true
                        }
                        "currentweather" -> {
                            config["location"] = "Wrocław,PL"
                            config["locationID"] = "3081368"
                            config["appid"] = "b81b05eb2425dcea5e92cadc30df5721"
                            config["colorIcon"] = true
                        }
                    }

                    WidgetSetup(module = widget.name, position = position, config = config)
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
