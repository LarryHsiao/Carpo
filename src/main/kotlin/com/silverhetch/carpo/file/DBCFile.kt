package com.silverhetch.carpo.file

import com.silverhetch.carpo.tag.Tags
import com.silverhetch.carpo.tag.DBFileTags
import java.sql.Connection

class DBCFile(private val dbConn: Connection, private val id: Int, private val name: String) : CFile {
    override fun title(): String {
        return name
    }

    override fun tags(): Tags {
        return DBFileTags(dbConn, id)
    }

    override fun remove() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}