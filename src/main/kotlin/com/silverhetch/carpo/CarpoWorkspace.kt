package com.silverhetch.carpo

import com.silverhetch.carpo.database.connection.CarpoDbConn
import com.silverhetch.clotho.database.SingleConn
import com.silverhetch.clotho.database.sqlite.SQLiteConn
import java.io.File
import java.io.FileFilter
import java.sql.Connection

class CarpoWorkspace(private val root: File) : Workspace {
    companion object {
        private const val DB_FILENAME = "carpo.db"
    }

    override fun files(): Array<File> {
        return root.listFiles(FileFilter {
            it.name != DB_FILENAME
        }) ?: arrayOf()
    }

    override fun sqlConn(): Connection {
        return SingleConn(
            CarpoDbConn(
                SQLiteConn(File(root, DB_FILENAME).absolutePath)
            )
        ).fetch()
    }

    override fun rootJFile(): File {
        return root
    }
}