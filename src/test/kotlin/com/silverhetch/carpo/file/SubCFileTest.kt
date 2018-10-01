package com.silverhetch.carpo.file

import com.silverhetch.carpo.file.phantom.PhantomCFile
import com.silverhetch.carpo.workspace.CarpoWorkspace
import org.junit.Assert
import org.junit.Test
import java.io.File
import java.nio.file.Files

class SubCFileTest {

    @Test
    fun simple() {
        Assert.assertEquals(
            2,
            SubCFile(
                CarpoWorkspace(
                    Files.createTempDirectory("").toFile()
                ),
                Files.createTempDirectory("").toFile().also { dir ->
                    File(dir, "1").createNewFile()
                    File(dir, "2").createNewFile()
                },
                PhantomCFile()
            ).subFiles().all().size
        )
    }

    /**
     * @see [simple] which the result remains same.
     */
    @Test
    fun shouldNotEffectedByCFile() {
        Assert.assertEquals(
            2,
            SubCFile(
                CarpoWorkspace(
                    Files.createTempDirectory("").toFile()
                ),
                Files.createTempDirectory("").toFile().also { dir ->
                    File(dir, "1").createNewFile()
                    File(dir, "2").createNewFile()
                },
                PhantomCFile(root = Files.createTempDirectory("").toFile().also { dir ->
                    File(dir, "1").createNewFile()
                })
            ).subFiles().all().size
        )
    }

    @Test
    fun propertyCheck() {
        val jdkFile = Files.createTempDirectory("").toFile().also { dir ->
            File(dir, "1").createNewFile()
            File(dir, "2").createNewFile()
        }
        SubCFile(
            CarpoWorkspace(
                Files.createTempDirectory("").toFile()
            ),
            jdkFile,
            PhantomCFile()
        ).let {
            Assert.assertEquals(
                "1",
                it.subFiles().all()["1"]!!.title()
            )

            Assert.assertEquals(
                jdkFile,
                it.jdkFile()
            )
        }
    }

    @Test
    fun remove() {
        val jdkFile = Files.createTempDirectory("").toFile().also { dir ->
            File(dir, "1").createNewFile()
            File(dir, "2").createNewFile()
        }
        SubCFile(
            CarpoWorkspace(
                Files.createTempDirectory("").toFile()
            ),
            jdkFile,
            PhantomCFile()
        ).let {
            it.remove()
            Assert.assertTrue(jdkFile.exists().not())
        }
    }

    @Test
    fun rename() {
        val root = Files.createTempDirectory("").toFile()
        val jdkFile = Files.createTempDirectory(root.toPath(), "").toFile().also {
            File.createTempFile("test", "1", it)
            File.createTempFile("test", "2", it)
        }
        SubCFile(
            CarpoWorkspace(
                root
            ),
            jdkFile,
            PhantomCFile()
        ).let {
            it.rename("NewName")

            Assert.assertEquals("NewName", it.jdkFile().name)
            Assert.assertTrue(it.jdkFile().exists())
            Assert.assertEquals(2, it.jdkFile().listFiles().size)
        }
    }

    @Test
    fun addFile_directoryCFile() {
        val jdkFile = Files.createTempDirectory("").toFile().also { dir ->
            File(dir, "1").createNewFile()
            File(dir, "2").createNewFile()
        }
        SubCFile(
            CarpoWorkspace(
                Files.createTempDirectory("").toFile()
            ),
            jdkFile,
            PhantomCFile()
        ).let {
            it.addFile(listOf(File.createTempFile("temp123", "")))
            Assert.assertEquals(
                3,
                it.subFiles().all().size
            )
        }
    }


    @Test
    fun addFile_moveToRoot() {
        val root = Files.createTempDirectory("").toFile()
        val jdkFile = Files.createTempFile(root.toPath(), "", "").toFile()
        SubCFile(
            CarpoWorkspace(
                Files.createTempDirectory("").toFile()
            ),
            jdkFile,
            PhantomCFile(root = root)
        ).let {
            it.addFile(listOf(File.createTempFile("temp123", "")))
            Assert.assertEquals(
                0,
                it.subFiles().all().size
            )

            Assert.assertEquals(
                2,
                root.listFiles().size
            )
        }
    }
}