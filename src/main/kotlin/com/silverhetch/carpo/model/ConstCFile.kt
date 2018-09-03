package com.silverhetch.carpo.model

import java.io.File

class ConstCFile(private val file: File) : CFile {
    private val tags = ArrayList<String>()
    override fun title(): String {
        return file.name
    }

    override fun tags(): List<String> {
        return tags.clone() as List<String>
    }

    override fun addTag(tag: String) {
        tags.add(tag)
    }

    override fun removeTag(tag: String) {
        tags.remove(tag)
    }
}