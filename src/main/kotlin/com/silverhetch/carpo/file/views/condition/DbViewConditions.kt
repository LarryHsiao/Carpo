package com.silverhetch.carpo.file.views.condition

import com.silverhetch.carpo.tag.Tag
import java.sql.Connection
import java.sql.SQLException

class DbViewConditions(private val connection: Connection, private val viewId: Long) : Conditions {
    override fun all(): List<Condition> {
        connection.prepareStatement("""
                select *
                from view_conditions
                where view_id=?;
            """).use { statement ->
            statement.setLong(1, viewId)
            statement.executeQuery().use {
                val result = ArrayList<Condition>()
                result.add(DbCondition(
                    connection,
                    it.getLong(it.findColumn("id")),
                    it.getLong(it.findColumn("tag_id")),
                    it.getLong(it.findColumn("view_id")),
                    it.getString(it.findColumn("type"))
                ))
                return result
            }
        }
    }

    override fun newCondition(tag: Tag, type: Condition.Type): Condition {
        connection.prepareStatement("""
            insert into view_conditions(view_id, tag_id, type)
            VALUES (?,?,?)
        """).use { statement ->
            statement.setLong(1, viewId)
            statement.setLong(2, tag.id())
            statement.setString(3,type.name)
            statement.executeUpdate()
            statement.generatedKeys.use {
                if (it.next()) {
                    return DbCondition(
                        connection,
                        it.getLong(1),
                        tag.id(),
                        viewId,
                        type.name
                    )
                } else {
                    throw SQLException("New condition failed")
                }
            }
        }
    }
}