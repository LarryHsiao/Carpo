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
            ).value(),
            2,
            "filename2"
        ).tags().let {
            /** @see [SampleDataConn] */
            assertEquals(1, it.all().size)
            assertEquals("tag2", it.all()["tag2"]!!.title())
        }
    }

    @Test
    fun byTagSingleQuote_checkWithSearch() {
        DBFiles(SampleDataConn(
            CarpoDbConn(
                InMemoryConn()
            )
        ).value()).let {
            /** @see [SampleDataConn] */
            it.all()["filename"]!!.tags().addTag("Quote'")
            assertEquals(
                "filename",
                it.byTag("Quote'")["filename"]!!.title()
            )
        }
    }


    @Test
    fun remove() {
        DBFiles(SampleDataConn(
            CarpoDbConn(
                InMemoryConn()
            )
        ).value()).let {
            /** @see [SampleDataConn] */
            it.byTag("tag")["filename"]!!.remove()
            assertEquals(0, it.byTag("tag1").size)
        }
    }

    @Test
    fun doNotSupportExecutable() {
        DBFiles(SampleDataConn(
            CarpoDbConn(
                InMemoryConn()
            )
        ).value()).let {
            /** @see [SampleDataConn] */
            try {
                it.all()["filename"]!!.executable().launch(object : CExecutable.Callback {
                    override fun onFailed() {
                        fail()
                    }
                })
                fail()
            } catch (e: UnsupportedOperationException) {
                assertTrue(true)
            }
        }
    }

    @Test
    fun doNotSupportJdkFile() {
        DBFiles(SampleDataConn(
            CarpoDbConn(
                InMemoryConn()
            )
        ).value()).let {
            /** @see [SampleDataConn] */
            try {
                it.all()["filename"]!!.jdkFile()
                fail()
            } catch (e: UnsupportedOperationException) {
                assertTrue(true)
            }
        }
    }

    @Test
    fun doNotSupportSubFiles() {
        DBFiles(SampleDataConn(
            CarpoDbConn(
                InMemoryConn()
            )
        ).value()).let {
            /** @see [SampleDataConn] */
            try {
                it.all()["filename"]!!.subFiles()
                fail()
            } catch (e: UnsupportedOperationException) {
                assertTrue(true)
            }
        }
    }



    @Test
    fun doNotSupportThumbnailUrl() {
        DBFiles(SampleDataConn(
            CarpoDbConn(
                InMemoryConn()
            )
        ).value()).let {
            /** @see [SampleDataConn] */
            try {
                it.all()["filename"]!!.thumbnailUrl()
                fail()
            } catch (e: UnsupportedOperationException) {
                assertTrue(true)
            }
        }
    }


    @Test
    fun doNotSupportAddFile() {
        DBFiles(SampleDataConn(
            CarpoDbConn(
                InMemoryConn()
            )
        ).value()).let {
            /** @see [SampleDataConn] */
            try {
                it.all()["filename"]!!.addFile(listOf())
                fail()
            } catch (e: UnsupportedOperationException) {
                assertTrue(true)
            }
        }
    }

    @Test
    fun rename() {
        DBFiles(SampleDataConn(
            CarpoDbConn(
                InMemoryConn()
            )
        ).value()).let {
            /** @see [SampleDataConn] */
            it.all()["filename"]!!.rename("The new Name")

            assertNull(it.all()["filename"])
        }
    }
}