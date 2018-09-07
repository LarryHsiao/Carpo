package com.silverhetch.carpo.tag

import com.silverhetch.carpo.database.connection.CarpoDbConn
import com.silverhetch.carpo.database.connection.SampleDataConn
import com.silverhetch.clotho.database.sqlite.InMemoryConn
import org.junit.Assert.*
import org.junit.Test

class DBTagsTest {
    @Test
    fun simple() {
        assertEquals(
            2,
            DBTags(
                SampleDataConn(
                    CarpoDbConn(
                        InMemoryConn()
                    )
                ).fetch()
            ).all().size
        )
    }

    @Test
    fun addTag() {
        assertEquals(
            3,
            DBTags(
                SampleDataConn(
                    CarpoDbConn(
                        InMemoryConn()
                    )
                ).fetch()
            ).let {
                it.addTag("Sample")
                it.all().size
            }
        )
    }
}