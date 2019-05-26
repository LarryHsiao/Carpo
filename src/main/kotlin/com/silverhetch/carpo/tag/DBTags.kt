package com.silverhetch.carpo.tag

import com.silverhetch.carpo.tag.factory.DBTagMapFactory
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
                return DBTagMapFactory(dbConn, it).value()
            }
        }
    }

    override fun addTag(name: String): Tag {
        dbConn.prepareStatement("""
            insert into tags(name) values (?)
        """).use { statement ->
            statement.setString(1, name)
            statement.executeUpdate()
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

    override fun byName(name: String): Map<String, Tag> {
        dbConn.prepareStatement("""
                    select *
                    from tags
                    where name like LOWER(?);
                  """).use { statement ->
            statement.setString(1, "%$name%")
            statement.executeQuery().use {
                return DBTagMapFactory(dbConn, it).value()
            }
        }
    }
}