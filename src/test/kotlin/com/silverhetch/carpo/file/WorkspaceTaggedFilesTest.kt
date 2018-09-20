package com.silverhetch.carpo.file

import com.silverhetch.carpo.database.connection.CarpoDbConn
import com.silverhetch.carpo.database.connection.SampleDataConn
import com.silverhetch.carpo.workspace.CarpoWorkspace
import com.silverhetch.clotho.database.sqlite.InMemoryConn
import org.junit.Assert.*
import org.junit.Test
import java.io.File
import java.nio.file.Files

class WorkspaceTaggedFilesTest {
    @Test
    fun all_notExistFile() {
        assertEquals(
            0,
            WorkspaceTaggedFiles(
                CarpoWorkspace(
                    Files.createTempDirectory("").toFile()
                ),
                DBFiles(
                    SampleDataConn(
                        CarpoDbConn(
                            InMemoryConn()
                        )
                    ).fetch()
                )
            ).all().size
        )
    }

    @Test
    fun all_existFile() {
        assertEquals(
            2,
            WorkspaceTaggedFiles(
                CarpoWorkspace(
                    Files.createTempDirectory("").toFile().also {
                        File(it, "filename").createNewFile()
                        File(it, "filename2").createNewFile()
                    }
                ),
                DBFiles(
                    SampleDataConn(
                        CarpoDbConn(
                            InMemoryConn()
                        )
                    ).fetch()
                )
            ).all().size
        )
    }

    @Test
    fun byTagName_existFile() {
        assertEquals(
            2,
            WorkspaceTaggedFiles(
                CarpoWorkspace(
                    Files.createTempDirectory("").toFile().also {
                        File(it, "filename").createNewFile()
                        File(it, "filename2").createNewFile()
                    }
                ),
                DBFiles(
                    SampleDataConn(
                        CarpoDbConn(
                            InMemoryConn()
                        )
                    ).fetch()
                )
            ).byTag("tag").size
        )
    }

    @Test
    fun byTagName_notExistFile() {
        assertEquals(
            0,
            WorkspaceTaggedFiles(
                CarpoWorkspace(
                    Files.createTempDirectory("").toFile()
                ),
                DBFiles(
                    SampleDataConn(
                        CarpoDbConn(
                            InMemoryConn()
                        )
                    ).fetch()
                )
            ).byTag("tag").size
        )
    }

    @Test
    fun addFile() {
        WorkspaceTaggedFiles(
            CarpoWorkspace(
                Files.createTempDirectory("").toFile().also {
                    File(it, "filename").createNewFile()
                    File(it, "filename2").createNewFile()
                }
            ),
            DBFiles(
                SampleDataConn(
                    CarpoDbConn(
                        InMemoryConn()
                    )
                ).fetch()
            )
        ).let {
            it.add(Files.createTempFile("", "").toFile())
            assertEquals(
                3,
                it.all().size
            )
        }
    }
}