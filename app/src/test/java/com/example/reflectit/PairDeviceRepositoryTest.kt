package com.example.reflectit

import com.example.reflectit.ui.device.available.pair.PairDeviceRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class PairDeviceRepositoryTest {
    private val paringCode = "444055"
    private val deviceId = "any string"
    private val hostname = "192.168.0.185:5000"
    private val pairRepositoryMock = PairDeviceRepository(hostname)

    @Test
    fun pairDeviceWithCorrectCode() {
        var token: String? = null
        runBlocking {
            token = pairRepositoryMock.pair(paringCode)
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