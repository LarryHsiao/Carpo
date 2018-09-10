package com.silverhetch.carpo

import com.silverhetch.carpo.workspace.CarpoWorkspace
import org.junit.Assert.*
import org.junit.Test
import java.io.File
import java.nio.file.Files

class CarpoImplTest {
    @Test
    fun queryEmptyDirectory() {
        assertEquals(
            0,
            CarpoImpl(
                CarpoWorkspace(File(
                    Files.createTempDirectory("prefix").toUri()
                ))
            ).all().size
        )
    }

    @Test
    fun newFile() {
        CarpoImpl(
            CarpoWorkspace(
                File(
                    Files.createTempDirectory("tempFile").toUri()
                )
            )
        ).let {
            it.addFile(listOf(File.createTempFile("this is tempFile", "")))
            assertEquals(1, it.all().size)
        }
    }

    @Test
    fun workspace() {
        CarpoWorkspace(
            File(
                Files.createTempDirectory("tempFile").toUri()
            )
        ).let { workspace ->
            assertEquals(
                workspace,
                CarpoImpl(
                    workspace
                ).workspace()
            )
        }
    }

    @Test
    fun byTag() {
        CarpoImpl(
            CarpoWorkspace(
                Files.createTempDirectory("prefix").toFile()
            )
        ).let {
            /**Sample data*/
            val newFile = File.createTempFile("prefix", "")
            it.addFile(listOf(newFile)).tags().addTag("Tag")

            assertEquals(
                newFile.name,
                it.byTag("Tag")[newFile.name]!!.title()
            )
        }
    }

    @Test
    fun allFile() {
        CarpoImpl(
            CarpoWorkspace(
                Files.createTempDirectory("prefix").toFile()
            )
        ).let {
            /**Sample data*/
            val newFile = File.createTempFile("prefix", "")
            it.addFile(listOf(newFile)).tags().addTag("Tag")

            assertEquals(
                newFile.name,
                it.all()[newFile.name]!!.title()
            )
        }
    }

    @Test
    fun searchByKeyword_file() {
        CarpoImpl(
            CarpoWorkspace(
                Files.createTempDirectory("prefix").toFile()
            )
        ).let {
            /**Sample data*/
            val newFile = File.createTempFile("prefix", "")
            it.addFile(listOf(newFile))

            assertEquals(
                newFile.name,
                it.byKeyword("prefix")[newFile.name]!!.title()
            )
        }
    }

    @Test
    fun searchByKeyword_tag() {
        CarpoImpl(
            CarpoWorkspace(
                Files.createTempDirectory("prefix").toFile()
            )
        ).let {
            /**Sample data*/
            val newFile = File.createTempFile("prefix", "")
            it.addFile(listOf(newFile)).tags().addTag("Sample Tag")

            assertEquals(
                newFile.name,
                it.byKeyword("Sample Tag")[newFile.name]!!.title()
            )
        }
    }

    @Test
    fun addFile_throwIfEmpty() {
        CarpoImpl(
            CarpoWorkspace(
                Files.createTempDirectory("prefix").toFile()
            )
        ).let {
            try{
                it.addFile(listOf())
                fail()
            }catch (e: IllegalArgumentException){
                assertTrue(true)
            }
        }
    }
}