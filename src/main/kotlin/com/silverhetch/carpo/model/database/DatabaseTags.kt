package com.silverhetch.carpo.model.database

import com.silverhetch.clotho.Source
import java.sql.Connection

class DatabaseTags(private val db: Source<Connection>, private val fileName: String) : Source<List<String>> {
    override fun fetch(): List<String> {
        db.fetch().createStatement().executeQuery(
            """
                select tags.name
                from tags
                join files as file, file_tag
                where file.name = '$fileName' and tags.id = file_tag.tag_id and file.id = file_tag.file_id;
                  """
        ).use {
            with(ArrayList<String>()) {
                while (it.next()) {
                    add(it.getString(it.findColumn("name")))
                }
                return this
            }
        }
    }

}