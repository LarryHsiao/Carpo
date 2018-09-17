package com.silverhetch.carpo.database

import com.silverhetch.carpo.database.connection.CarpoDbConn
import com.silverhetch.carpo.database.connection.SampleDataConn
import com.silverhetch.carpo.tag.DBFileTags
import com.silverhetch.clotho.database.sqlite.InMemoryConn
import org.junit.Assert.assertEquals
import org.junit.Test

class DBFileTagsTest {
    @Test
    fun simple() {
        assertEquals(
            2,// according to sample data filename has 2 tag
            DBFileTags(
                SampleDataConn(
                    CarpoDbConn(
                        InMemoryConn()
                    )
                ).fetch(), 1
            ).all().size
        )
    }

    @Test
    fun simple2() {
        assertEquals(
            1,// according to sample data filename2 has one tag
            DBFileTags(
                SampleDataConn(
                    CarpoDbConn(
                        InMemoryConn()
                    )
                ).fetch(), 2
            ).all().size
        )
    }


    @Test
    fun addTag() {
        assertEquals(
            2,// according to sample data filename2 has one tag, this case plus 1
            DBFileTags(
                SampleDataConn(
                    CarpoDbConn(
                        InMemoryConn()
                    )
                ).fetch(), 2
            ).let {
                it.addTag("NewTag22")
                it.all().size
            }
        )
    }

    @Test
    fun searchTag() {
        assertEquals(
            1,
            DBFileTags(
                SampleDataConn(
                    CarpoDbConn(
                        InMemoryConn()
                    )
                ).fetch(), 2
            ).let {
                it.addTag("NewTag22")
                it.byName("NewTag22").size
            }
        )
    }
}