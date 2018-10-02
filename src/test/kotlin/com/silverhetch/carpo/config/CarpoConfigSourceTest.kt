package com.silverhetch.carpo.config

import org.junit.Assert
import org.junit.Test
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.nio.file.Files

class CarpoConfigSourceTest {

    @Test
    fun notExist_usingDefault() {
        val tempConfig = Files.createTempFile("", "").toFile()
        Assert.assertEquals(
            System.getProperty("user.dir") + "/Playground",
            CarpoConfigSource(tempConfig.absolutePath).fetch().workspacePath()
        )
    }

    @Test
    fun exist() {
        val tempConfig = Files.createTempFile("", "").toFile().also {
            val writer = PrintWriter(FileWriter(it, false))
            writer.printf("workspace=/home/abc/def")
            writer.close()
        }
        Assert.assertEquals(
            "/home/abc/def",
            CarpoConfigSource(tempConfig.absolutePath).fetch().workspacePath()
        )
    }

    @Test
    fun newWorkspace_notExist() {
        val tempConfig = Files.createTempFile("", "").toFile().also {
            val writer = PrintWriter(FileWriter(it, false))
            writer.printf("workspace=/home/abc/def")
            writer.close()
        }
        val config = CarpoConfigSource(tempConfig.absolutePath).fetch()
        config.changeWorkspacePath(File("/newFileLocation"))
        Assert.assertEquals(
            "/home/abc/def",
            config.workspacePath()
        )
    }

    @Test
    fun newWorkspace_exist() {
        val tempConfig = Files.createTempFile("", "").toFile().also {
            val writer = PrintWriter(FileWriter(it, false))
            writer.printf("workspace=/home/abc/def")
            writer.close()
        }
        val newWorkspace = Files.createTempDirectory("").toFile()
        val config = CarpoConfigSource(tempConfig.absolutePath).fetch()
        config.changeWorkspacePath(newWorkspace)
        Assert.assertEquals(
            newWorkspace.absolutePath,
            config.workspacePath()
        )
    }

    @Test
    fun nonExistConfigFile_ableChangeWorkspace() {
        val tempConfig = Files.createTempFile("", "").toFile().also {
            it.deleteRecursively()
        }
        val newWorkspace = Files.createTempDirectory("").toFile()
        val config = CarpoConfigSource(tempConfig.absolutePath).fetch()
        config.changeWorkspacePath(newWorkspace)
        Assert.assertEquals(
            newWorkspace.absolutePath,
            CarpoConfigSource(tempConfig.absolutePath).fetch().workspacePath()
        )
    }
}