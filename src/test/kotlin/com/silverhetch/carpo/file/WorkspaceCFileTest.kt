package com.silverhetch.carpo.file

import com.silverhetch.carpo.workspace.CarpoWorkspace
import com.silverhetch.carpo.database.connection.CarpoDbConn
import com.silverhetch.carpo.database.connection.SampleDataConn
import com.silverhetch.carpo.file.phantom.PhantomCFile
import com.silverhetch.clotho.database.sqlite.InMemoryConn
import org.junit.Assert
import org.junit.Test
import java.io.File
import java.nio.file.Files

class WorkspaceCFileTest {
    @Test
    fun fileRemove() {
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
    fun fileRename() {
        Files.createTempDirectory("prefix").toFile().also { root ->
            File.createTempFile("prifx", "", root).let { file ->
                WorkspaceCFile(
                    CarpoWorkspace(
                        root
                    ),
                    PhantomCFile(file.name)
                ).rename("newName")
                Assert.assertTrue(File(root, "newName").exists())
            }
        }
    }

    @Test
    fun fileControl_deleteFileGroup() {
        Files.createTempDirectory("prefix").toFile().also { root ->
            Files.createTempDirectory(root.toPath(), "prifx").toFile().let { groupRoot ->
                File.createTempFile("prefix", "", groupRoot)
                File.createTempFile("prefix", "", groupRoot)
                WorkspaceCFile(
                    CarpoWorkspace(
                        root
                    ),
                    PhantomCFile(groupRoot.name)
                ).remove()
                Assert.assertFalse(groupRoot.exists())
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
                ).value(),
                2,
                "filename2"
            )
        ).let {
            Assert.assertEquals("filename2", it.title())
            Assert.assertEquals(1, it.tags().all().size)
        }
    }

    @Test
    fun newJdkFile() {
        Files.createTempDirectory("prefix").toFile().also { root ->
            Files.createTempDirectory(root.toPath(), "prifx").toFile().let { groupRoot ->
                File.createTempFile("prefix", "", groupRoot)
                File.createTempFile("prefix", "", groupRoot)

                val newFile = File.createTempFile("newFIle", "")
                WorkspaceCFile(
                    CarpoWorkspace(
                        root
                    ),
                    PhantomCFile(groupRoot.name)
                ).addFile(listOf(newFile))

                Assert.assertTrue(File(groupRoot, newFile.name).exists())
            }
        }
    }
}