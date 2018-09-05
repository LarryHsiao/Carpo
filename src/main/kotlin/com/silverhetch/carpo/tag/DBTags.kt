package com.silverhetch.carpo.tag

import com.silverhetch.carpo.tag.factory.TagListFactory
import java.sql.Connection

class DBTags(private val dbConn: Connection) : Tags {
    override fun all(): List<Tag> {
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

    override fun addTag(name: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}