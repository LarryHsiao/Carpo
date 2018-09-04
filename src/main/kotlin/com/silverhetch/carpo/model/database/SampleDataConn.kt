package com.silverhetch.carpo.model.database

import com.silverhetch.clotho.Source
import java.sql.Connection

/**
 * Database connection with initial data.
 */
class SampleDataConn(private val db: Source<Connection>) : Source<Connection> {
    override fun fetch(): Connection {
        return db.fetch().also {
            it.createStatement().execute("insert into files(name) values ('filename');")
            it.createStatement().execute("insert into files(name) values ('filename2');")
            it.createStatement().execute("insert into tags(name) values ('tag');")
            it.createStatement().execute("insert into tags(name) values ('tag2');")
            it.createStatement().execute("insert into file_tag(file_id, tag_id) values (1, 1);")
            it.createStatement().execute("insert into file_tag(file_id, tag_id) values (1, 2);")
            it.createStatement().execute("insert into file_tag(file_id, tag_id) values (2, 2);")
        }
    }
}