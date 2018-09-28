package com.silverhetch.carpo.config

import org.junit.Assert
import org.junit.Test
import java.io.FileWriter
import java.io.PrintWriter
import java.nio.file.Files

class CarpoConfigSourceTest {

    @Test
    fun notExist_usingDefault() {
        val tempConfig = Files.createTempFile("", "").toFile()
        Assert.assertEquals(
            System.getProperty("user.dir"),
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
}