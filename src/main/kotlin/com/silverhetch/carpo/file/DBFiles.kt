package com.silverhetch.carpo.file

import com.silverhetch.carpo.file.factory.FileListFactory
import java.sql.Connection

class DBFiles(private val db: Connection) : Files {
    override fun all(): List<CFile> {
        db.createStatement().use { statement ->
            statement.executeQuery("""
                select *
                from files;
            """).use {
                return FileListFactory(db, it).fetch()
            }
        }
    }

    override fun add(fileName: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}