package com.silverhetch.carpo.tag.factory

import com.silverhetch.carpo.database.connection.CarpoDbConn
import com.silverhetch.carpo.database.connection.SampleDataConn
import com.silverhetch.carpo.tag.DBTags
import com.silverhetch.carpo.tag.PhantomTag
import com.silverhetch.clotho.database.sqlite.InMemoryConn
import org.junit.Assert.*
import org.junit.Test

class UriTagFactoryTest {

    @Test
    fun toUri() {
        assertEquals(
            "carpo://silverhetch.com/tag/TagName123",
            UriTagFactory.TagUri(PhantomTag("TagName123")).value()
        )
    }

    @Test
    fun toTag() {
        assertEquals(
            2,
            UriTagFactory.UriTag("carpo://silverhetch.com/tag/tag2", DBTags(
                SampleDataConn(
                    CarpoDbConn(
                        InMemoryConn()
                    )
                ).value()
            )).value().id()
        )
    }

    @Test
    fun validUri_success() {
        assertTrue(UriTagFactory().isValidUri("carpo://silverhetch.com/tag/ff"))
    }

    @Test
    fun validUri_failed() {
        assertFalse(UriTagFactory().isValidUri("carpo://silverhetch.com/tag")) // miss the separator
        assertFalse(UriTagFactory().isValidUri("carpo://silverhetch.com/object"))
    }
}