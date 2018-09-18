package com.silverhetch.carpo.file

import com.silverhetch.carpo.file.factory.DBFileListFactory
import java.io.File
import java.sql.Connection
import java.sql.SQLException

/**
 * [Files] object that has tag attached which is presented by tagId.
 */
class DBTagFiles(private val dbConn: Connection, private val tagId: Long) : Files {
    override fun all(): Map<String, CFile> {
        dbConn.createStatement().use { statement ->
            statement.executeQuery("""
                SELECT files.* from file_tag
                left join files on file_tag.file_id=files.id
                where file_tag.tag_id=$tagId
            """).use {
                return DBFileListFactory(dbConn, it).fetch()
            }
        }
    }

    override fun byTag(tagName: String): Map<String, CFile> {
        dbConn.prepareStatement("""
                SELECT files.* from file_tag
                left join files on file_tag.file_id=files.id
                left join tags on file_tag.tag_id=tags.id
                where file_tag.tag_id=$tagId and tags.name like ?
            """).use { statement ->
            statement.setString(1, "%$tagName%")
            statement.executeQuery().use {
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
            statement.generatedKeys.use { resultSet ->
                if (resultSet.next()) {
                    resultSet.getLong(1).let { newFileId ->
                        return DBCFile(
                            dbConn,
                            newFileId,
                            file.name
                        ).also {
                            attachTagById(newFileId)
                        }
                    }
                } else {
                    throw SQLException("Insertion failed")
                }
            }
        }
    }

    private fun attachTagById(fileId: Long) {
        dbConn.createStatement().use { statement ->
            statement.executeUpdate("""
                INSERT or ignore into file_tag(file_id, tag_id) values ($fileId, $tagId)"""
            )
        }
    }
}