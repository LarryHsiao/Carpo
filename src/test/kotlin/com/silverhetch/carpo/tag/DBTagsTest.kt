package com.silverhetch.carpo.tag

import com.silverhetch.carpo.database.connection.CarpoDbConn
import com.silverhetch.carpo.database.connection.SampleDataConn
import com.silverhetch.clotho.database.sqlite.InMemoryConn
import org.junit.Assert.assertEquals
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
                ).value()
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
                ).value()
            ).let {
                it.addTag("Sample")
                it.all().size
            }
        )
    }

    @Test
    fun addTagSingleQuote() {
        assertEquals(
            3,
            DBTags(
                SampleDataConn(
                    CarpoDbConn(
                        InMemoryConn()
                    )
                ).value()
            ).let {
                it.addTag("Sample'")
                it.all().size
            }
        )
    }

    @Test
    fun byTagTest() {
        assertEquals(
            1,
            DBTags(
                SampleDataConn(
                    CarpoDbConn(
                        InMemoryConn()
                    )
                ).value()
            ).let {
                it.addTag("newTag'")
                it.byName("newTag").size
            }
        )
    }
}