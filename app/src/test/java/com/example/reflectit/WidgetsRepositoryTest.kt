package com.example.reflectit

import com.example.reflectit.ui.data.services.WidgetSetup
import com.example.reflectit.ui.data.services.Position
import com.example.reflectit.ui.data.services.Widget
import com.example.reflectit.ui.data.services.WidgetDetails
import com.example.reflectit.ui.device.available.details.widgets.WidgetsRepository
import kotlinx.coroutines.*
import org.junit.Assert.assertEquals
import org.junit.Test

class WidgetsRepositoryTest {
    private val hostname = "localhost:5000"
    private val tokenMock =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IjEiLCJuYmYiOjE1NTY5NzA4MjQsImV4cCI6MTU1NzA1NzIyNCwiaWF0IjoxNTU2OTcwODI0fQ.CR3p36tcW_DQ0v_oObGH-N2ixWSapef4QU7YVOSMeh8"
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
        val configurationMock = listOf(
            WidgetSetup("clock", position = Position.TopLeft),
            WidgetSetup("clock", position = Position.BottomLeft)
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