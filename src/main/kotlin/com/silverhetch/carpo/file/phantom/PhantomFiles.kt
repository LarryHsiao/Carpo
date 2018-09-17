package com.silverhetch.carpo.file.phantom

import com.silverhetch.carpo.file.CFile
import com.silverhetch.carpo.file.Files

class PhantomFiles : Files {
    override fun all(): Map<String, CFile> {
        return mapOf()
    }

    override fun byTag(tagName: String): Map<String, CFile> {
        return mapOf()
    }

    override fun add(fileName: String): CFile {
        return PhantomCFile()
    }
}