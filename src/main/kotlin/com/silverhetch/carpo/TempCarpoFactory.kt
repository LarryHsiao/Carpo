package com.silverhetch.carpo

import com.silverhetch.clotho.Source
import com.silverhetch.clotho.database.sqlite.InMemoryConn
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