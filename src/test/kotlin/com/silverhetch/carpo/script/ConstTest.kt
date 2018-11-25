package com.silverhetch.carpo.script

import com.silverhetch.carpo.Consts
import org.junit.Assert
import org.junit.Test

class ConstTest {
    @Test
    fun checkVersion() {
        Assert.assertTrue(
            Consts.VERSION.isNotEmpty()
        )
    }
}