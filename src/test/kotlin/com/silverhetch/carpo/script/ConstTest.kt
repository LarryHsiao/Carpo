package com.silverhetch.carpo.script

import com.silverhetch.BuildConfig
import org.junit.Assert
import org.junit.Test

class ConstTest {
    @Test
    fun checkVersion() {
        Assert.assertTrue(
            BuildConfig.VERSION.isNotEmpty()
        )
    }
}