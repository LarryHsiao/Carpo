package com.silverhetch.carpo.tag

import com.silverhetch.carpo.database.connection.CarpoDbConn
import com.silverhetch.carpo.database.connection.SampleDataConn
import com.silverhetch.carpo.workspace.CarpoWorkspace
import com.silverhetch.clotho.database.sqlite.InMemoryConn
import org.junit.Assert.assertEquals
import org.junit.Test
import java.nio.file.Files

class WorkspaceTagsTest {
    @Test
    fun byNameSearch() {
        assertEquals(2,
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
            ).byName("tag").size
        )
    }
}