package com.example.reflectit

import com.example.reflectit.ui.device.available.pair.PairDeviceRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class PairDeviceRepositoryTest {
    private val paringCode = "679910"
    private val deviceId = "any string"
    private val hostname = "localhost:5000"
    private val pairRepositoryMock = PairDeviceRepository(hostname)

    @Test
    fun pairDeviceWithCorrectCode() {
        var token: String? = null
        runBlocking {
            token = pairRepositoryMock.pair("679910")
        }
        assertEquals(true, token != null)
    }

    @Test
    fun pairDeviceWithWrongCode() {
        var token: String? = null
        runBlocking {
            token = pairRepositoryMock.pair("679900")
        }
        assertEquals(true, token == null)
    }

}