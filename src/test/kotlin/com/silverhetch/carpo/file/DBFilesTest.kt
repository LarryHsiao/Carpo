package com.silverhetch.carpo.file

import com.silverhetch.carpo.database.connection.CarpoDbConn
import com.silverhetch.clotho.database.sqlite.InMemoryConn
import org.junit.Assert.*
import org.junit.Test
import java.nio.file.Files

class DBFilesTest {
    @Test
    fun noInitialData() {
        assertEquals(
            0,
            DBFiles(
                CarpoDbConn(
                    InMemoryConn()
                ).value()
            ).all().size
        )
    }

    @Test
    fun addFile() {
        val newFile = Files.createTempFile("", "").toFile()
        DBFiles(
            CarpoDbConn(
                InMemoryConn()
            ).value()
        ).let {
            it.add(newFile)
            assertEquals(1, it.all().size)
            assertEquals(newFile.name, it.all()[newFile.name]!!.title())
        }
    }


}