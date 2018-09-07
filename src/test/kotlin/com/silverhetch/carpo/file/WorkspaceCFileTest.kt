package com.silverhetch.carpo.file

import com.silverhetch.carpo.CarpoWorkspace
import com.silverhetch.carpo.database.connection.CarpoDbConn
import com.silverhetch.carpo.database.connection.SampleDataConn
import com.silverhetch.clotho.database.sqlite.InMemoryConn
import org.junit.Assert
import org.junit.Test
import java.io.File
import java.nio.file.Files

class WorkspaceCFileTest {
    @Test
    fun fileControl() {
        Files.createTempDirectory("prefix").toFile().also { root ->
            File.createTempFile("prifx", "", root).let { file ->
                WorkspaceCFile(
                    CarpoWorkspace(
                        root
                    ),
                    PhantomCFile(file.name)
                ).remove()
                Assert.assertFalse(file.exists())
            }
        }
    }

    @Test
    fun cFileDelegate() {
        WorkspaceCFile(
            CarpoWorkspace(
                Files.createTempDirectory(
                    "prifix"
                ).toFile()
            ),
            DBCFile(
                SampleDataConn(
                    CarpoDbConn(
                        InMemoryConn()
                    )
                ).fetch(),
                2,
                "filename2"
            )
        ).let {
            Assert.assertEquals("filename2", it.title())
            Assert.assertEquals(1, it.tags().all().size)
        }
    }
}