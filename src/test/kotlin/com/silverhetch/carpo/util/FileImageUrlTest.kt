package com.silverhetch.carpo.util

import org.junit.Assert.*
import org.junit.Test
import java.nio.file.Files

class FileImageUrlTest {
    @Test
    fun exactlyImage() {
        Files.createTempFile("", ".jpg").toFile().let { imageFile ->
            assertEquals(
                imageFile.toURI().toString(),
                FileImageUrl(
                    imageFile,
                    "Default"
                ).fetch()
            )
        }
    }

    @Test
    fun fileNotImage() {
        assertEquals(
            "Default",
            FileImageUrl(
                Files.createTempFile("", "").toFile(),
                "Default"
            ).fetch()
        )
    }

    @Test
    fun directoryNoImage() {
        Files.createTempDirectory("").toFile().let {
            Files.createTempFile(it.toPath(), "", "")
            assertEquals(
                "Default",
                FileImageUrl(
                    it,
                    "Default"
                ).fetch()
            )
        }
    }

    @Test
    fun directoryHasImage() {
        Files.createTempDirectory("").toFile().let { root ->
            assertEquals(
                Files.createTempFile(root.toPath(), "", ".jpg").toFile().toURI().toString(),
                FileImageUrl(
                    root,
                    "Default"
                ).fetch()
            )
        }
    }
}