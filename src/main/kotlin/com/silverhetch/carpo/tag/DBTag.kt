package com.silverhetch.carpo.tag

import java.sql.Connection

class DBTag(private val db: Connection, private val id: Long, private val name: String) : Tag {
    override fun id(): Long {
        return id
    }

    override fun title(): String {
        return name
    }
}