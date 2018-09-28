package com.silverhetch.carpo.config

import org.junit.Assert.assertTrue
import org.junit.Test

class ConstConfigTest {
    @Test
    fun simple() {
        assertTrue(ConstConfig().workspacePath().isNotEmpty())
    }
}