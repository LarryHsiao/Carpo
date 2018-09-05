package com.silverhetch.carpo.tag

import java.sql.Connection

class DBTag(private val db: Connection, private val name: String) : Tag {
    override fun title(): String {
        return name
    }
}