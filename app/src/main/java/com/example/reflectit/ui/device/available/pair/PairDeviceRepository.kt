package com.example.reflectit.ui.device.available.pair

import com.example.reflectit.ui.data.services.ParingService
import kotlinx.coroutines.runBlocking

class PairDeviceRepository(hostname: String) {

    private val paringService = ParingService.create(hostname)

    suspend fun pair(code: String): String? { //returns auth token
        val deviceId = "any string just for now"
        val request = paringService.parryAsync(code, deviceId)
            val response = request.await()
            if (response.isSuccessful) {
                return response.body()?.token
            }
        return null
    }

    suspend fun getMirrorId(): String? {
        var mirrorId = ""
        val request = paringService.getMirrorIdAsync()

        return runBlocking {
            val response = request.await()
            return@runBlocking if (response.isSuccessful) {
                response.body()?.id!!
            } else
                null
        }
    }
}