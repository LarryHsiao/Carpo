package com.silverhetch.carpo.file.factory

import com.silverhetch.carpo.file.CFile
import com.silverhetch.carpo.file.DBCFile
import com.silverhetch.clotho.Source
import java.sql.Connection
import java.sql.ResultSet

/**
 * Factory that builds [CFile] map from database querying result.
 */
class FileListFactory(private val dbConn: Connection, private val it: ResultSet) : Source<Map<String, CFile>> {
    override fun fetch(): Map<String, CFile> {
        HashMap<String, CFile>().let { result ->
            while (it.next()) {
                val name = it.getString(it.findColumn("name"))
                result[name] = DBCFile(
                    dbConn,
                    it.getLong(it.findColumn("id")),
                    name
                )
            }
            return result
        }
    }
}