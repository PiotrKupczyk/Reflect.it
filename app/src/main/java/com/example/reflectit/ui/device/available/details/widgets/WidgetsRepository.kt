package com.example.reflectit.ui.device.available.details.widgets

import com.example.reflectit.ui.data.services.WidgetSetup
import com.example.reflectit.ui.data.services.Widget
import com.example.reflectit.ui.data.services.WidgetDetails
import com.example.reflectit.ui.data.services.WidgetsService
import com.google.gson.GsonBuilder

class WidgetsRepository(hostname: String, private val token: String) {

    private val widgetService = WidgetsService.create(hostname, token)

    suspend fun getAllWidgets(): List<Widget>? {
        val request = widgetService.getAllWidgetsAsync()
        val response = request.await()
        return if (response.isSuccessful) response.body()
        else null
    }

    suspend fun updateConfiguration(configurations: List<WidgetSetup>): Int? {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val config = gson.toJson(configurations)
        val request = widgetService.sendConfigurationAsync(config)
        val response = request.await()
        return response.code()
    }

    suspend fun getWidgetDetails(id: String): WidgetDetails? {
        val request = widgetService.getWidgetDetailsAsync(id)
        val response = request.await()
        return if (response.isSuccessful) {
            response.body()
        } else
            null
    }
}