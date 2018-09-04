package com.silverhetch.carpo.model.database

import com.silverhetch.clotho.database.sqlite.InMemoryConn
import org.junit.Assert.assertEquals
import org.junit.Test

class DatabaseTagsTest {
    @Test
    fun simple() {
        assertEquals(
            2,// according to sample data filename has 2 tag
            DatabaseTags(
                SampleDataConn(
                    CarpoDbConn(
                        InMemoryConn()
                    )
                ), "filename"
            ).fetch().size
        )
    }

    @Test
    fun simple2() {
        assertEquals(
            1,// according to sample data filename2 has one tag
            DatabaseTags(
                SampleDataConn(
                    CarpoDbConn(
                        InMemoryConn()
                    )
                ), "filename2"
            ).fetch().size
        )
    }
}