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

                statement.execute("""
                  create table if not exists views(
                    id integer primary key autoincrement ,
                    name text not null unique
                  );
                """)

                statement.execute("""
                   create table if not exists view_conditions(
                      id integer primary key autoincrement,
                      view_id integer not null,
                      tag_id integer not null,
                      type text not null,
                      unique (view_id, tag_id)
                   );
                """)
            }
        }
    }
}