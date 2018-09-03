package com.silverhetch.carpo.model

import org.junit.Assert.*
import org.junit.Test
import java.nio.file.Files

class ConstCFileTest {

    @Test
    fun tagSimple() {
        ConstCFile(
            Files.createTempDirectory("prefix").toFile()
        ).let {
            it.addTag("Tag01")
            assertEquals(
                "Tag01",
                it.tags()[0]
            )
        }
    }
}