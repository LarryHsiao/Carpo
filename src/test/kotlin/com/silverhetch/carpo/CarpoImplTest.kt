package com.silverhetch.carpo

import org.junit.Assert.*
import org.junit.Test
import java.io.File
import java.nio.file.Files

class CarpoImplTest {
    @Test
    fun queryEmptyDirectory() {
        assertEquals(
            0,
            CarpoImpl(
                CarpoWorkspace(File(
                    Files.createTempDirectory("prefix").toUri()
                ))
            ).all().size
        )
    }

    @Test
    fun newFile() {
        CarpoImpl(
            CarpoWorkspace(
                File(
                    Files.createTempDirectory("tempFile").toUri()
                )
            )
        ).let {
            it.addFile(File.createTempFile("this is tempFile", ""))
            assertEquals(1, it.all().size)
        }
    }
}