package com.silverhetch.carpo.tag.factory

import com.silverhetch.carpo.tag.Tag
import com.silverhetch.carpo.tag.DBTag
import com.silverhetch.clotho.Source
import java.sql.Connection
import java.sql.ResultSet

class TagListFactory(private val dbConn: Connection, private val it: ResultSet) : Source<List<Tag>> {
    override fun fetch(): List<Tag> {
        ArrayList<Tag>().let { result ->
            while (it.next()) {
                result.add(
                    DBTag(
                        dbConn,
                        it.getString(
                            it.findColumn("name")
                        )
                    )
                )
            }
            return result
        }
    }
}