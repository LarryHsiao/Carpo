package com.silverhetch.carpo.file

import com.silverhetch.carpo.file.phantom.PhantomCFile
import com.silverhetch.carpo.workspace.CarpoWorkspace
import org.junit.Assert
import org.junit.Test
import java.io.File
import java.nio.file.Files

class SubFilesTest {
    @Test
    fun empty() {
        Assert.assertEquals(
            0,
            SubFiles(
                CarpoWorkspace(
                    Files.createTempFile("", "").toFile()
                ),
                PhantomCFile()
            ).all().size
        )
    }

    @Test
    fun size() {
        Assert.assertEquals(
            2,
            SubFiles(
                CarpoWorkspace(
                    Files.createTempFile("", "").toFile()
                ),
                PhantomCFile(root = Files.createTempDirectory("").toFile().also { dir ->
                    File(dir, "1").createNewFile()
                    File(dir, "2").createNewFile()
                })
            ).all().size
        )
    }

    @Test
    fun byTag_phantomCFile() {
        Assert.assertEquals(
            0,
            SubFiles(
                CarpoWorkspace(
                    Files.createTempFile("", "").toFile()
                ),
                PhantomCFile(root = Files.createTempDirectory("").toFile().also { dir ->
                    File(dir, "1").createNewFile()
                    File(dir, "2").createNewFile()
                })
            ).byTag("123").size
        )
    }

    @Test
    fun addFile() {
        SubFiles(
            CarpoWorkspace(
                Files.createTempFile("", "").toFile()
            ),
            PhantomCFile(root = Files.createTempDirectory("").toFile().also { dir ->
                File(dir, "1").createNewFile()
                File(dir, "2").createNewFile()
            })
        ).let {
            it.add(Files.createTempFile("", "").toFile())
            Assert.assertEquals(
                3,
                it.all().size
            )
        }
    }

}