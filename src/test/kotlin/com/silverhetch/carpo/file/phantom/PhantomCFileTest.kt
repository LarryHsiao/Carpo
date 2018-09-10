package com.silverhetch.carpo.file.phantom

import com.silverhetch.carpo.file.CExecutable
import org.junit.Assert.*
import org.junit.Test
import java.io.File

class PhantomCFileTest {
    @Test
    fun nothingShouldHappened() {
        PhantomCFile().also {
            it.remove()
            it.executable().launch(object : CExecutable.Callback {
                override fun onFailed() {
                    fail()
                }
            })
            it.tags().addTag("abc")
            it.title()
            it.addFile(listOf(
                File.createTempFile("Prefix", "")
            ))

            assertTrue(true)
        }
    }
}