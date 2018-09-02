package com.silverhetch.carpo.model

import com.silverhetch.carpo.model.factory.TempCarpoFactory
import java.io.File
import java.nio.file.Files

class PhantomCarpo : Carpo {
    private val carpo: Carpo = TempCarpoFactory().fetch()

    override fun all(): List<CFile> {
        return carpo.all()
    }

    override fun addFile(file: File) {
        return addFile(file)
    }
}