package com.silverhetch.carpo.database.connection

import com.silverhetch.clotho.Source
import java.sql.Connection

/**
 * Carpo database connection.
 */
class CarpoDbConn(private val conn: Source<Connection>) : Source<Connection> {
    override fun fetch(): Connection {
        return conn.fetch().also {
            it.createStatement().use { statement ->
                statement.execute("""
                    create table if not exists file_tag (
                      id      integer primary key autoincrement,
                      file_id integer not null,
                      tag_id  integer not null,
                      unique (file_id, tag_id)
                      );"""
                )
                statement.execute("""
                    create table if not exists tags (
                      id   integer primary key autoincrement,
                      name text not null,
                      unique (name)
                    );
                    """
                )
                statement.execute("""
                   create table if not exists files (
                      id   integer primary key autoincrement,
                      name text not null,
                      unique (name)
                    );
                """)
            }
        }
    }
}