package com.example.reflectit

import com.example.reflectit.ui.data.services.*
import com.example.reflectit.ui.device.available.details.widgets.WidgetsRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import org.junit.Assert.assertEquals
import org.junit.Test

class WidgetsRepositoryTest {
    private val hostname = "localhost:5000"
    private val tokenMock =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6InFzZGFkIiwibmJmIjoxNTU3MDg4NzI0LCJleHAiOjE1NTcxNzUxMjQsImlhdCI6MTU1NzA4ODcyNH0.0T-ygNxOtqICbWmXwXT51w96EqVD5N7J_g1-DgJSq7Q"
    private val widgetsRepository = WidgetsRepository(hostname, tokenMock)

    @Test
    fun getAllWidgets() {
        var result: List<Widget>? = null
        runBlocking {
            result = widgetsRepository.getAllWidgets()
            println(result)
        }
        assertEquals(true, result != null)
    }

    @Test
    fun updateConfiguration() {
        val config = mutableMapOf<String, String>()
        val widget = Widget(4, "weatherforecast", WidgetCategory.Weather, "")
        val position = Position.getPositionByIndex(-3) ?: Position.TopLeft
        if (widget.category == WidgetCategory.Weather) { // just for now
            config["location"] = "Wrocław,PL"
            config["locationID"] = "3081368"
            config["appid"] = "b81b05eb2425dcea5e92cadc30df5721"
        }
        val configurationMock = listOf(
            WidgetSetup("clock", position = Position.TopLeft),
                    WidgetSetup(widget.name, position = position, config = config)
//            WidgetSetup("clock", position = Position.BottomLeft)
        )
        var statusCode: Int? = 0
        runBlocking {
            statusCode = widgetsRepository.updateConfiguration(configurationMock)
        }
        assertEquals(200, statusCode)
    }

    @Test
    fun widgetDetails() {
        val idMock = "1"
        var response:WidgetDetails? = null
        runBlocking {
            response = widgetsRepository.getWidgetDetails(idMock)
            println(response)
        }
        assertEquals(true, response != null)
    }
}