package com.silverhetch.carpo.threads

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

object WorkerThread {
    private val instance = Executors.newSingleThreadScheduledExecutor()

    fun getInstance(): ScheduledExecutorService {
        return instance
    }
}