package com.silverhetch.carpo.tag

import com.silverhetch.carpo.tag.factory.TagListFactory
import java.sql.Connection

class DBFileTags(private val dbConn: Connection, private val id: Int) : Tags {
    override fun all(): List<Tag> {
        dbConn.createStatement().use { statement ->
            statement.executeQuery(
                """
                select tags.name
                from tags
                join files as file, file_tag
                where file.id = '$id' and tags.id = file_tag.tag_id and file.id = file_tag.file_id;
                  """
            ).use {
                return TagListFactory(dbConn, it).fetch()
            }
        }
    }

    override fun addTag(name: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}