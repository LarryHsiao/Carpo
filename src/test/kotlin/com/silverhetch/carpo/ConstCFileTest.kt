package com.silverhetch.carpo

import org.junit.Assert.*
import org.junit.Test
import java.nio.file.Files

class ConstCFileTest {

    @Test
    fun tagSimple() {
        ConstCFile(
            Files.createTempDirectory("prefix").toFile()
        ).let {
            it.tags().addTag("Tag01")
            assertEquals(
                "Tag01",
                it.tags().all()[0]
            )
        }
    }
}