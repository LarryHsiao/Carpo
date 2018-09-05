package com.silverhetch.carpo.file.factory

import com.silverhetch.carpo.file.CFile
import com.silverhetch.carpo.file.DBCFile
import com.silverhetch.clotho.Source
import java.sql.Connection
import java.sql.ResultSet

class FileListFactory(private val dbConn: Connection, private val it: ResultSet) : Source<List<CFile>> {
    override fun fetch(): List<CFile> {
        ArrayList<CFile>().let { result ->
            while (it.next()) {
                result.add(
                    DBCFile(
                        dbConn,
                        it.getInt(it.findColumn("id")),
                        it.getString(it.findColumn("name"))
                    )
                )
            }
            return result
        }
    }
}