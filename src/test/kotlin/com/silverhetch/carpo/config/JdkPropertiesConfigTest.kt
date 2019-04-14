package com.silverhetch.carpo.config

import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import java.io.FileWriter
import java.io.PrintWriter
import java.nio.file.Files

class JdkPropertiesConfigTest{

    @Test
    fun workspace_notExist() {
        val tempConfig = Files.createTempFile("", "").toFile().also {
            val writer = PrintWriter(FileWriter(it, false))
            writer.printf("workspace=/home/abc/def")
            writer.close()
        }
        val config = CarpoConfigSource(tempConfig.absolutePath).fetch()
        Assert.assertEquals(
            ConstConfig().workspacePath(),
            config.workspacePath()
        )
    }
}