package com.silverhetch.carpo.database.connection

import com.silverhetch.clotho.Source
import java.sql.Connection

/**
 * Carpo database connection.
 */
class CarpoDbConn(private val conn: Source<Connection>) : Source<Connection> {
    companion object {
        private const val DATABASE_VERSION = 1
    }

    override fun fetch(): Connection {
        return conn.fetch().also { conn ->
            var currentVer = 0
            conn.createStatement().executeQuery("PRAGMA user_version;").use {
                currentVer = it.getInt(1)
            }
            when {
                currentVer == 0 -> {
                    initialDatabase(conn)
                }
                currentVer < DATABASE_VERSION -> {
                    updateDatabase(conn, currentVer)
                }
                else -> {
                    // Initial done, nothing we should do.
                }
            }

            conn.createStatement().use {
                it.execute("PRAGMA user_version = $DATABASE_VERSION;")
            }
        }
    }

    private fun initialDatabase(conn: Connection) {
        conn.createStatement().use { statement ->
            statement.execute("""
                    create table file_tag (
                      id      integer primary key autoincrement,
                      file_id integer not null,
                      tag_id  integer not null,
                      unique (file_id, tag_id)
                      );"""
            )
            statement.execute("""
                    create table tags (
                      id   integer primary key autoincrement,
                      name text not null,
                      updateTime DATE,
                      unique (name)
                    );
                    """
            )
            statement.execute("""
            create trigger insert_tags_update_time after insert on tags
            begin
              update tags set updateTime = DATETIME('NOW')
              where rowid = new.rowid;
            end;
        """)
            statement.execute("""
            create trigger update_tags_update_time after update on tags
            begin
              update tags set updateTime = DATETIME('NOW')
              where rowid = new.rowid;
            end;
        """)

            statement.execute("""
                   create table files (
                      id   integer primary key autoincrement,
                      name text not null,
                      updateTime DATE,
                      unique (name)
                    );
                """)

            statement.execute("""
            create trigger insert_files_update_time
              after insert
              on files
            begin
              update files
              set updateTime = DATETIME('NOW')
              where rowid = new.rowid;
            end;
        """)

            statement.execute("""
            create trigger update_files_update_time
              after update
              on files
            begin
              update files
              set updateTime = DATETIME('NOW')
              where rowid = new.rowid;
            end;
        """)

            statement.execute("""
                  create table views(
                    id integer primary key autoincrement ,
                    name text not null unique
                  );
                """)

            statement.execute("""
                   create table view_conditions(
                      id integer primary key autoincrement,
                      view_id integer not null,
                      tag_id integer not null,
                      type text not null,
                      unique (view_id, tag_id)
                   );
                """)
        }
    }

    private fun updateDatabase(conn: Connection, version: Int) {
        TODO("Nothing to do for now.")
    }
}