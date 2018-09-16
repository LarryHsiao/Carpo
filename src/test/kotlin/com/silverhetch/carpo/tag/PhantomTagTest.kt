package com.silverhetch.carpo.tag

import org.junit.Assert.*
import org.junit.Test

class PhantomTagTest {
    @Test
    fun phantomInDarkness() {
        PhantomTag().also {
            assertEquals(-1, it.id())
            assertEquals("Phantom", it.title())
        }
    }
}