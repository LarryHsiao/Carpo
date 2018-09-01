package com.silverhetch.carpo

import com.silverhetch.carpo.model.CarpoImpl
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
                File(
                    Files.createTempDirectory("prefix").toUri()
                )
            ).all().size
        )
    }


}