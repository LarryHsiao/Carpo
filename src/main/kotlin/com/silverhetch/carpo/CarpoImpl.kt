package com.silverhetch.carpo

import com.silverhetch.carpo.database.connection.CarpoDbConn
import com.silverhetch.carpo.file.*
import com.silverhetch.clotho.database.sqlite.SQLiteConn
import java.io.File
import java.sql.Connection

class CarpoImpl(private val workspace: File) : Carpo {
    private val dbConn: Connection = CarpoDbConn(
        SQLiteConn(workspace.absolutePath)
    ).fetch()
    private val dbFiles: Files

    init {
        this.dbFiles = DBFiles(dbConn)
    }

    override fun workspace(): File {
        return workspace
    }

    override fun all(): List<CFile> {
        dbFiles.all().let { dbFiles ->
            workspace.listFiles()?.also { fileArray ->
                return MutableList(fileArray.size) {
                    TODO()
                }
            }
        }
        return listOf()
    }

    override fun addFile(file: File) {
        file.renameTo(File(workspace, file.name))
        dbFiles.add(file.name)
    }

    override fun byTag(tag: String): List<CFile> {
        TODO()
    }
}