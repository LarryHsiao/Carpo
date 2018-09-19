package com.silverhetch.carpo.tag

import com.silverhetch.carpo.database.connection.CarpoDbConn
import com.silverhetch.carpo.database.connection.SampleDataConn
import com.silverhetch.clotho.database.sqlite.InMemoryConn
import org.junit.Assert
import org.junit.Test

class DBTagTest {
    @Test
    fun rename() {
        DBTag(
            SampleDataConn(
                CarpoDbConn(
                    InMemoryConn()
                )
            ).fetch(), 1, "tag1"
        ).let {
            it.rename("THIS is new name.")
            Assert.assertEquals(
                "THIS is new name.",
                it.title()
            )
        }
    }

    @Test
    fun remove() {
        DBTags(
            SampleDataConn(
                CarpoDbConn(
                    InMemoryConn()
                )
            ).fetch()).let {
            it.byName("tag")["tag"]!!.remove()
            Assert.assertNull(
                it.byName("tag")["tag"]
            )
        }
    }

    @Test
    fun tagFiles() {
        DBTag(
            SampleDataConn(
                CarpoDbConn(
                    InMemoryConn()
                )
            ).fetch(), 2, "tag2"
        ).let {
            Assert.assertEquals(
                2,
                it.files().all().size
            )
        }
    }
}