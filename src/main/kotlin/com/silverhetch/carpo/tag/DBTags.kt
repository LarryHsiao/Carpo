package com.silverhetch.carpo.tag

import com.silverhetch.carpo.tag.factory.TagListFactory
import java.sql.Connection
import java.sql.SQLException

/**
 * Database implementation of [Tags].
 */
class DBTags(private val dbConn: Connection) : Tags {
    override fun all(): Map<String, Tag> {
        dbConn.createStatement().use { statement ->
            statement.executeQuery(
                """
                    select *
                    from tags;
                  """
            ).use {
                return TagListFactory(dbConn, it).fetch()
            }
        }
    }

    override fun addTag(name: String): Tag {
        dbConn.createStatement().use { statement ->
            statement.execute(
                """
                    insert into tags(name) values ('$name')
                """
            )
            statement.generatedKeys.use {
                if (it.next()) {
                    return DBTag(
                        dbConn,
                        it.getLong(1),
                        name
                    )
                } else {
                    throw SQLException("insert new tag failed")
                }
            }
        }
    }
}