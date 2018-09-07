package com.silverhetch.carpo.tag.factory

import com.silverhetch.carpo.tag.Tag
import com.silverhetch.carpo.tag.DBTag
import com.silverhetch.clotho.Source
import java.sql.Connection
import java.sql.ResultSet

class TagListFactory(private val dbConn: Connection, private val it: ResultSet) : Source<Map<String, Tag>> {
    override fun fetch(): Map<String, Tag> {
        HashMap<String, Tag>().let { result ->
            while (it.next()) {
                val name = it.getString(
                    it.findColumn("name")
                )
                result[name] =
                    DBTag(
                        dbConn,
                        it.getLong(it.findColumn("id")),
                        name
                    )
            }
            return result
        }
    }
}