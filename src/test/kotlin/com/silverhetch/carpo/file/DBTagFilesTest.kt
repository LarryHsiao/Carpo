package com.silverhetch.carpo.file

import com.silverhetch.carpo.database.connection.CarpoDbConn
import com.silverhetch.carpo.database.connection.SampleDataConn
import com.silverhetch.clotho.database.sqlite.InMemoryConn
import org.junit.Assert.*
import org.junit.Test
import java.io.File

class DBTagFilesTest {
    @Test
    fun searchByTag() {
        assertEquals(
            1,
            DBTagFiles(
                SampleDataConn( // sample data in this class
                    CarpoDbConn(
                        InMemoryConn()
                    )
                ).fetch()
                , 1
            ).byTag("tag").size
        )
    }


    @Test
    fun searchOtherTags() {
        assertEquals(
            2,
            DBTagFiles(
                SampleDataConn( // sample data in this class
                    CarpoDbConn(
                        InMemoryConn()
                    )
                ).fetch()
                , 2
            ).byTag("tag").size
        )
    }

    @Test
    fun allFiles() {
        assertEquals(
            2,
            DBTagFiles(
                SampleDataConn( // sample data in this class
                    CarpoDbConn(
                        InMemoryConn()
                    )
                ).fetch()
                , 2
            ).all().size
        )
    }

    @Test
    fun addFiles() {
        assertEquals(
            3,
            DBTagFiles(
                SampleDataConn( // sample data in this class
                    CarpoDbConn(
                        InMemoryConn()
                    )
                ).fetch()
                , 2
            ).let {
                it.add(File.createTempFile("123", ""))
                it.all().size
            }
        )
    }
}