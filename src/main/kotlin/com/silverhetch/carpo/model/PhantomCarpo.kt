package com.silverhetch.carpo.model

import java.io.File
import java.nio.file.Files

class PhantomCarpo : Carpo {
    private val carpo: Carpo = CarpoImpl(
        File(
            Files.createTempDirectory("tempFile").toUri()
        )
    )

    override fun all(): List<CFile> {
        return carpo.all()
    }

    override fun addFile(file: File) {
        return addFile(file)
    }
}