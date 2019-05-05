package com.example.reflectit.ui.device.available.details.widgets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.example.reflectit.ui.data.services.WidgetSetup
import com.example.reflectit.ui.data.services.Position
import com.example.reflectit.ui.data.services.Widget
import com.example.reflectit.ui.data.services.WidgetCategory
import com.example.reflectit.ui.extensions.appendAsync
import kotlinx.coroutines.*

//class WidgetPosition(val widget: Widget, val position: Position? = null)

class SharedWidgetsSelectorViewModel(private val repository: WidgetsRepository) : ViewModel() {
    val selectedWidgets = MutableLiveData<ArrayList<Widget>>().apply { postValue(arrayListOf()) }

    fun selectWidget(element: Widget) {
        selectedWidgets.appendAsync(element)
    }

    fun loadWidgets(): LiveData<ArrayList<Widget>> {
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
                    val widget = selectedWidgets.value!![it]
                    val position = Position.getPositionByIndex(it)!!
                    WidgetSetup(widget.name, position = position )
                }
            if (configurations != null) {
                CoroutineScope(Dispatchers.Default).launch {
                    val code = repository.updateConfiguration(configurations)
                }
            } else return
        } else return
        //TODO send correct configuration
    }
}
