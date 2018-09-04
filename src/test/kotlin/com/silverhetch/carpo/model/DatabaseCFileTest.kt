package com.silverhetch.carpo.model

import com.silverhetch.carpo.model.database.DatabaseCFile
import com.silverhetch.clotho.database.sqlite.InMemoryConn
import org.junit.Assert.assertEquals
import org.junit.Test
import java.nio.file.Files

class DatabaseCFileTest {

    @Test
    fun tagSimple() {
        DatabaseCFile(
            InMemoryConn(),
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