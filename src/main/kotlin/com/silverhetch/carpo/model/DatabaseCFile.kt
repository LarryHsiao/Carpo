package com.silverhetch.carpo.model

import com.silverhetch.clotho.Source
import java.io.File
import java.sql.Connection

/**
 * Decorator of CFile using Database to store the extra information.
 */
class DatabaseCFile(private val db: Source<Connection>, private val file: File) : CFile {
    override fun title(): String {
        return file.name
    }

    override fun tags(): List<String> {
        TODO()
    }

    override fun addTag(tag: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeTag(tag: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}