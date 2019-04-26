package com.example.reflectit

import com.example.reflectit.ui.data.services.MirrorAuthResponse
import com.example.reflectit.ui.device.available.pair.PairDeviceRepository
import kotlinx.coroutines.*
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ParryingServiceTest {
    @Test
    fun coroutinesTest() {
        val hostname =  "localhost:5000"
        val pairRepository = PairDeviceRepository(hostname)

        val result = pairRepository.getMirrorId()
        assertEquals(true,  result != null )
    }
        // this is your first suspending function

    @Test
    fun parringServiceTest() {
        val hostname =  "localhost:5000"
        val pairRepository = PairDeviceRepository(hostname)
        val token = pairRepository.pair("679910").value
        assertEquals(true, token != null)

    }
}