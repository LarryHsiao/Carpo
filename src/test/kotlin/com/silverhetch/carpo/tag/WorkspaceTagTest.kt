package com.silverhetch.carpo.tag

import com.silverhetch.carpo.database.connection.CarpoDbConn
import com.silverhetch.carpo.database.connection.SampleDataConn
import com.silverhetch.carpo.workspace.CarpoWorkspace
import com.silverhetch.clotho.database.sqlite.InMemoryConn
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import java.nio.file.Files

class WorkspaceTagTest {
    @Test
    fun rename() {
        assertEquals("newName",
            WorkspaceTag(
                CarpoWorkspace(
                    Files.createTempDirectory("").toFile()
                ),
                DBTag(
                    SampleDataConn(
                        CarpoDbConn(
                            InMemoryConn()
                        )
                    ).fetch(), 1, ""
                )
            ).let {
                it.rename("newName")
                it.title()
            })
    }

    @Test
    fun remove() {
        WorkspaceTags(
            CarpoWorkspace(
                Files.createTempDirectory("").toFile()
            ),
            DBTags(
                SampleDataConn(
                    CarpoDbConn(
                        InMemoryConn()
                    )
                ).fetch()
            )
        ).let {
            it.byName("tag")["tag"]!!.remove()
            Assert.assertNull(
                it.byName("tag")["tag"]
            )
        }
    }
}