package com.example.reflectit.ui.device.available.details.widgets

import com.example.reflectit.ui.data.services.Configuration
import com.example.reflectit.ui.data.services.Widget
import com.example.reflectit.ui.data.services.WidgetsService
import com.google.gson.GsonBuilder

class WidgetsRepository(hostname: String, private val token: String) {

    private val widgetService = WidgetsService.create(hostname)

    suspend fun getAllWidgets(): List<Widget>? {
        val request = widgetService.getAllWidgetsAsync("Bearer $token")
        val response = request.await()
        return if (response.isSuccessful) response.body()
        else null
    }

    suspend fun sendConfiguration(cofigurations: List<Configuration>): Int? {
        val gson = GsonBuilder().create()
        val request = widgetService.sendConfigurationAsync(gson.toJson(cofigurations))
        val response = request.await()
        return if (response.isSuccessful) {
            response.code()
        } else
            null
    }
}