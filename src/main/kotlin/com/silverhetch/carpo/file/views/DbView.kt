package com.silverhetch.carpo.file.views

import com.silverhetch.carpo.file.views.condition.Conditions
import com.silverhetch.carpo.file.views.condition.DbViewConditions
import java.sql.Connection

class DbView(private val connection: Connection,
             private val id: Long,
             private val name: String) : View {

    override fun id(): Long {
        return id
    }

    override fun name(): String {
        return name
    }

    override fun remove() {
        connection.prepareStatement("""
                delete from views
                where id=?;
            """).use {
            it.setLong(1, id)
            it.executeUpdate()
        }
    }

    override fun conditions(): Conditions {
        return DbViewConditions(connection, id)
    }
}