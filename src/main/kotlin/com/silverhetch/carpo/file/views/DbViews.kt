package com.silverhetch.carpo.file.views

import java.sql.Connection
import java.sql.SQLException

/**
 * Views stores in database.
 */
class DbViews(private val dbConn: Connection) : Views {
    override fun all(): List<View> {
        dbConn.createStatement().use { statement ->
            statement.executeQuery("""
                select *
                from views;
            """).use {
                val result = ArrayList<View>()
                while (it.next()) {
                    result.add(
                        DbView(
                            dbConn,
                            it.getLong(it.findColumn("id")),
                            it.getString(it.findColumn("name"))
                        )
                    )
                }
                return result
            }
        }
    }

    override fun newView(name: String): View {
        dbConn.prepareStatement("""
            insert into views(name) values (?);
        """).use { statement ->
            statement.setString(1, name)
            statement.executeUpdate()
            statement.generatedKeys.use {
                if (it.next()) {
                    return DbView(
                        dbConn,
                        it.getLong(1),
                        name
                    )
                } else {
                    throw SQLException("insertion failed")
                }
            }
        }
    }
}