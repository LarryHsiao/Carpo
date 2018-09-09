package com.silverhetch.carpo.tag

import com.silverhetch.carpo.tag.factory.TagListFactory
import java.sql.Connection

/**
 * Database implementation of [Tags] which a [com.silverhetch.carpo.file.CFile] have.
 */
class DBFileTags(private val dbConn: Connection, private val fileId: Long) : Tags {
    private val dbTags = DBTags(dbConn)

    override fun all(): Map<String, Tag> {
        dbConn.createStatement().use { statement ->
            statement.executeQuery(
                """
                select tags.*
                from tags
                join files as file, file_tag
                where file.id = '$fileId' and tags.id = file_tag.tag_id and file.id = file_tag.file_id;
                  """
            ).use {
                return TagListFactory(dbConn, it).fetch()
            }
        }
    }

    override fun addTag(name: String): Tag {
        dbTags.all().let { existTags ->
            return existTags[name] ?: dbTags.addTag(name).also {
                newFileTagLink(it)
            }
        }

    }

    private fun newFileTagLink(tag: Tag) {
        dbConn.createStatement().use { statement ->
            statement.execute("""
                            insert or ignore into file_tag(file_id,tag_id)
                            values ($fileId, ${tag.id()})
                         """
            )
        }
    }
}