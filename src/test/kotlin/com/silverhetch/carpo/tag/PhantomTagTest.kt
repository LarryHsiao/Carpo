package com.silverhetch.carpo.tag

import org.junit.Assert.assertEquals
import org.junit.Test

class PhantomTagTest {
    @Test
    fun phantomInDarkness() {
        PhantomTag().also {
            it.remove()
            assertEquals("Phantom", it.title())
            it.rename("NewName")
            assertEquals("NewName", it.title())
            assertEquals(0, it.files().all().size)
            assertEquals(-1, it.id())
        }
    }
}