package com.silverhetch.carpo.file

import com.silverhetch.carpo.database.connection.CarpoDbConn
import com.silverhetch.clotho.database.sqlite.InMemoryConn
import org.junit.Assert.*
import org.junit.Test

class DBFilesTest {
    @Test
    fun noInitialData() {
        assertEquals(
            0,
            DBFiles(
                CarpoDbConn(
                    InMemoryConn()
                ).fetch()
            ).all().size
        )
    }

    @Test
    fun addFile() {
        DBFiles(
            CarpoDbConn(
                InMemoryConn()
            ).fetch()
        ).let {
            it.add("Name")
            assertEquals(1, it.all().size)
            assertEquals("Name", it.all()["Name"]!!.title())
        }
    }


}