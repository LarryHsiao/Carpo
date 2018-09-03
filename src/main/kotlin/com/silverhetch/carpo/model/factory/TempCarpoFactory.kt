package com.silverhetch.carpo.model.factory

import com.silverhetch.carpo.model.Carpo
import com.silverhetch.carpo.model.CarpoImpl
import com.silverhetch.clotho.Source
import java.io.File
import java.nio.file.Files

/**
 * Carpo implementation which using temporary directory as workspace.
 */
class TempCarpoFactory : Source<Carpo> {
    override fun fetch(): Carpo {
        return CarpoImpl(
            File(
                Files.createTempDirectory(
                    "tempFile"
                ).toUri()
            )
        )
    }
}