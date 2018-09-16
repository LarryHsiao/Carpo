package com.silverhetch.carpo.tag

import org.junit.Assert.*
import org.junit.Test

class PhantomTagsTest {
    @Test
    fun simple() {
        PhantomTags().also {
            assertEquals(0, it.all().size)
        }
    }
}