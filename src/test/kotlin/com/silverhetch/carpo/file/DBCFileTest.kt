package com.silverhetch.carpo.file

import com.silverhetch.carpo.database.connection.CarpoDbConn
import com.silverhetch.carpo.database.connection.SampleDataConn
import com.silverhetch.clotho.database.sqlite.InMemoryConn
import org.junit.Assert.*
import org.junit.Test

class DBCFileTest {
    @Test
    fun tag() {
        DBCFile(
            SampleDataConn(
                CarpoDbConn(
                    InMemoryConn()
                )
            ).fetch(),
            2,
            "filename2"
        ).tags().let {
            /** @see [SampleDataConn] */
            assertEquals(1, it.all().size)
            assertEquals("tag2", it.all()[0].title())
        }
    }
}