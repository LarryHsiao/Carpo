package com.silverhetch.carpo.tag

import com.silverhetch.carpo.file.DBTagFiles
import com.silverhetch.carpo.file.Files
import java.sql.Connection

/**
 * Database implementation of [Tag]
 */
class DBTag(private val db: Connection, private val id: Long, private var name: String) : Tag {
    override fun id(): Long {
        return id
    }

    override fun title(): String {
        return name
    }

    override fun files(): Files {
        return DBTagFiles(db, id)
    }

    override fun rename(newName: String) {
        db.prepareStatement("""
            update tags
            set name=?
            where id=$id
        """).use { statement ->
            statement.setString(1, newName)
            statement.executeUpdate()
            this.name = newName
        }
    }

    override fun remove() {
        db.prepareStatement("""
                delete from tags
                where id=?;
            """).use {
            it.setLong(1, id)
            it.executeUpdate()
        }
        db.prepareStatement("""
                delete from file_tag
                where tag_id=?;
        """).use {
            it.setLong(1, id)
            it.executeUpdate()
        }
    }
}