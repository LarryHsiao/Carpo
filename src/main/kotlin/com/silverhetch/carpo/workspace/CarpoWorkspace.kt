package com.silverhetch.carpo.workspace

import com.silverhetch.carpo.database.connection.CarpoDbConn
import com.silverhetch.clotho.database.SingleConn
import com.silverhetch.clotho.database.sqlite.SQLiteConn
import java.io.File
import java.io.FileFilter
import java.nio.file.NotDirectoryException
import java.sql.Connection

/**
 * Carpo`s implementation of [Workspace]
 */
class CarpoWorkspace(private val root: File) : Workspace {
    companion object {
        private const val DB_FILENAME = "carpo.db"
    }

    override fun files(): Array<File> {
        if (!root.isDirectory) {
            throw NotDirectoryException("Worksspace only can be directory")
        }
        return root.listFiles(FileFilter {
            it.name != DB_FILENAME
        })
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

    override fun insertionPipe(): Workspace.FilePipe {
        return CopyFilePipe()
    }

    private class CopyFilePipe : Workspace.FilePipe{
        override fun through(source: File, target: File) {
            source.renameTo(target)
        }
    }
}