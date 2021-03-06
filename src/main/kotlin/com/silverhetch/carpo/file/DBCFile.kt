package com.silverhetch.carpo.file

import com.silverhetch.carpo.tag.DBFileTags
import com.silverhetch.carpo.tag.Tags
import java.io.File
import java.sql.Connection

/**
 * A implementation of [CFile] with database.
 */
class DBCFile(private val dbConn: Connection, private val id: Long, private var name: String) : CFile {
    override fun title(): String {
        return name
    }

    override fun thumbnailUrl(): String {
        throw UnsupportedOperationException("Should create decorators to support this function")
    }

    override fun tags(): Tags {
        return DBFileTags(dbConn, id)
    }

    override fun remove() {
        dbConn.createStatement().use {
            it.execute("delete from files where files.id = '$id';")
        }
    }

    override fun rename(newName: String) {
        dbConn.prepareStatement("update files set name=? where id=$id;").use {
            it.setString(1, newName)
            it.executeUpdate()
        }
        name = newName
    }

    override fun executable(): CExecutable {
        throw UnsupportedOperationException("Should create decorators for this very launch method")
    }

    override fun addFile(file: List<File>) {
        throw UnsupportedOperationException("Should create decorators to support this function")
    }

    override fun jdkFile(): File {
        throw UnsupportedOperationException("Create decorators to support this function")
    }

    override fun subFiles(): Files {
        throw UnsupportedOperationException("Database not handling File system things.")
    }
}