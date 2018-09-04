package com.silverhetch.carpo.model.database

import com.silverhetch.carpo.model.CFile
import com.silverhetch.clotho.Source
import java.io.File
import java.sql.Connection

/**
 * Decorator of CFile using Database to store the extra information.
 */
class DatabaseCFile(db: Source<Connection>, private val file: File) : CFile {
    private val db = CarpoDbConn(db)

    override fun title(): String {
        return file.name
    }

    override fun tags(): List<String> {
        return DatabaseTags(db, title()).fetch()
    }

    override fun addTag(tag: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeTag(tag: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}