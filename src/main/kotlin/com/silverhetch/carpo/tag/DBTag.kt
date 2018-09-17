package com.silverhetch.carpo.tag

import com.silverhetch.carpo.file.Files
import java.sql.Connection

/**
 * Database implementation of [Tag]
 */
class DBTag(private val db: Connection, private val id: Long, private val name: String) : Tag {
    override fun id(): Long {
        return id
    }

    override fun title(): String {
        return name
    }

    override fun files(): Files {
        TODO()
    }
}