package com.example.reflectit.ui.device.available.details.widgets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.example.reflectit.ui.data.services.WidgetSetup
import com.example.reflectit.ui.data.services.Position
import com.example.reflectit.ui.data.services.Widget
import com.example.reflectit.ui.extensions.appendAsync
import kotlinx.coroutines.*

class SharedWidgetsSelectorViewModel(val repository: WidgetsRepository) : ViewModel() {
    val selectedWidgets = MutableLiveData<ArrayList<Widget>>()

    fun selectWidget(widget: Widget) {
        selectedWidgets.appendAsync(widget)
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
        val configurations = mutableListOf<WidgetSetup>()
        //TODO send correct configuration
        selectedWidgets.value?.forEach { widget ->
            configurations.add(WidgetSetup(widget.name, position = Position.TopLeft))
        }
        CoroutineScope(Dispatchers.Default).launch {
            val code = repository.updateConfiguration(configurations)
        }
    }
}
