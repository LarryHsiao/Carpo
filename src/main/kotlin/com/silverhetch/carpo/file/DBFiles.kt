package com.silverhetch.carpo.file

import com.silverhetch.carpo.file.factory.DBFileListFactory
import java.io.File
import java.sql.Connection
import java.sql.SQLException

/**
 * A implementation of [Files]
 */
class DBFiles(private val dbConn: Connection) : Files {
    override fun all(): Map<String, CFile> {
        dbConn.createStatement().use { statement ->
            statement.executeQuery("""
                select *
                from files;
            """).use {
                return DBFileListFactory(dbConn, it).fetch()
            }
        }
    }

    override fun add(file: File): CFile {
        dbConn.prepareStatement("""
            insert into files(name) values (?);
        """).use { statement ->
            statement.setString(1, file.name)
            statement.executeUpdate()
            statement.generatedKeys.use {
                if (it.next()) {
                    return DBCFile(
                        dbConn,
                        it.getLong(1),
                        file.name
                    )
                } else {
                    throw SQLException("insertion failed")
                }
            }
        }
    }

    override fun byTag(tagName: String): Map<String, CFile> {
        dbConn.prepareStatement("""
                select files.*
                from files
                join tags, file_tag
                where LOWER(tags.name) like LOWER(?) and tags.id = file_tag.tag_id and files.id = file_tag.file_id
                ;
            """).use { statement ->
            statement.setString(1, "%$tagName%")
            statement.executeQuery().use {
                return DBFileListFactory(dbConn, it).fetch()
            }
        }
    }
}