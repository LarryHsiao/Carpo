package com.silverhetch.carpo.workspace

import com.silverhetch.carpo.CarpoImpl
import org.junit.Assert
import org.junit.Test
import java.io.File
import java.nio.file.Files

class WorkspaceMergingTest {
    @Test
    fun simple_checkFile() {
        val distWorkspace = CarpoImpl(
            CarpoWorkspace(
                Files.createTempDirectory("").toFile().also {
                    File(it, "filename3").createNewFile()
                    File(it, "filename4").createNewFile()
                }
            )
        )
        WorkspaceMerging(
            CarpoImpl(
                CarpoWorkspace(
                    Files.createTempDirectory("").toFile().also {
                        File(it, "filename").createNewFile()
                        File(it, "filename2").createNewFile()
                    }
                )
            ), distWorkspace

        ).fetch()

        Assert.assertEquals(
            4,
            distWorkspace.all().size
        )
    }

    @Test
    fun simple_checkTag() {
        val fromWorkspace = CarpoImpl(
            CarpoWorkspace(
                Files.createTempDirectory("").toFile().also {
                    File(it, "filename").createNewFile()
                    File(it, "filename2").createNewFile()
                }
            )
        ).also {
            it.all().forEach { Key, CFile ->
                CFile.tags().addTag(Key) // tag name same as file name
            }
        }
        val distWorkspace = CarpoImpl(
            CarpoWorkspace(
                Files.createTempDirectory("").toFile().also {
                    File(it, "filename3").createNewFile()
                    File(it, "filename4").createNewFile()
                }
            )
        )

        WorkspaceMerging(
            fromWorkspace,
            distWorkspace
        ).fetch()

        distWorkspace.all().let{
            Assert.assertEquals(
                "filename",
                it["filename"]!!.tags().all()["filename"]!!.title()
            )
            Assert.assertEquals(
                "filename2",
                it["filename2"]!!.tags().all()["filename2"]!!.title()
            )
        }
    }
}