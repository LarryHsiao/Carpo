package com.silverhetch.carpo.file.phantom

import com.silverhetch.carpo.file.CExecutable
import com.silverhetch.carpo.file.CFile
import com.silverhetch.carpo.tag.PhantomTags
import com.silverhetch.carpo.tag.Tags
import java.io.File
import java.nio.file.Files

/**
 * Phantom class of [CFile]
 */
class PhantomCFile(private val title: String = "Phantom") : CFile {
    private val file = Files.createTempFile("", "").toFile()

    override fun title(): String {
        return title
    }

    override fun thumbnailUrl(): String {
        return "https://google.com"
    }

    override fun tags(): Tags {
        return PhantomTags()
    }

    override fun rename(newName: String) {
        // leave empty in phantom class
    }

    override fun remove() {
        // leave empty in phantom class
    }

    override fun executable(): CExecutable {
        return PhantomExecutable()
    }

    override fun addFile(file: List<File>) {
        // leave empty in phantom class
    }

    override fun jdkFile(): File {
        return file
    }
}