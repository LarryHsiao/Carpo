package com.silverhetch.carpo.file.factory

import com.silverhetch.carpo.database.connection.CarpoDbConn
import com.silverhetch.carpo.database.connection.SampleDataConn
import com.silverhetch.carpo.file.DBFiles
import com.silverhetch.carpo.workspace.CarpoWorkspace
import com.silverhetch.clotho.database.sqlite.InMemoryConn
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File
import java.nio.file.Files

class WorkspaceMapFactoryTest {

    @Test
    fun nonExistInWorkspace() {
        assertEquals(0,
            WorkspaceMapFactory(
                CarpoWorkspace(
                    Files.createTempDirectory("").toFile()
                ),
                DBFiles(
                    SampleDataConn(
                        CarpoDbConn(
                            InMemoryConn()
                        )
                    ).value()
                ).all()
            ).value().size
        )
    }

    @Test
    fun existINWorkspace() {
        assertEquals(2,
            WorkspaceMapFactory(
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
                    ).value()
                ).all()
            ).value().size
        )
    }
}
