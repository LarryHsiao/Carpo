package com.silverhetch.carpo.file

import com.silverhetch.carpo.tag.Tags
import com.silverhetch.carpo.tag.DBFileTags
import java.sql.Connection

/**
 * A implementation of [CFile] with database.
 */
class DBCFile(private val dbConn: Connection, private val id: Long, private val name: String) : CFile {
    override fun title(): String {
        return name
    }

    override fun tags(): Tags {
        return DBFileTags(dbConn, id)
    }

    override fun remove() {
        dbConn.createStatement().use {
            it.execute("delete from files where files.id = '$id';")
        }
    }
}