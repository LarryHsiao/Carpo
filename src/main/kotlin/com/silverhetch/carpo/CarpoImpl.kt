package com.silverhetch.carpo

import com.silverhetch.carpo.file.CFile
import com.silverhetch.carpo.file.DBFiles
import com.silverhetch.carpo.file.Files
import com.silverhetch.carpo.file.WorkspaceCFile
import java.io.File

/**
 * Main logic implementation of Carpo.
 */
class CarpoImpl(private val workspace: Workspace) : Carpo {
    private val dbFiles: Files

    init {
        this.dbFiles = DBFiles(workspace.sqlConn())
    }

    override fun workspace(): Workspace {
        return workspace
    }

    override fun all(): Map<String, CFile> {
        val dbFileMap = dbFiles.all()
        workspace.files().also { jfiles ->
            HashMap<String, CFile>().let { result ->
                jfiles.forEach { jFile ->
                    result[jFile.name] = WorkspaceCFile(
                        workspace,
                        if (dbFileMap.containsKey(jFile.name)) {
                            dbFileMap[jFile.name]!!
                        } else {
                            dbFiles.add(jFile.name)
                        }
                    )
                }
                return result
            }
        }
        return mapOf()
    }

    override fun addFile(file: File) :CFile{
        file.renameTo(File(workspace.rootJFile(), file.name))
        return dbFiles.add(file.name)
    }

    override fun byTag(tag: String): Map<String, CFile> {
        return dbFiles.byTag(tag).filter {
            File(workspace.rootJFile(), it.key).exists()
        }.also { dbMap ->
            return HashMap<String, CFile>().also { result ->
                dbMap.forEach { key, value ->
                    result[key] = WorkspaceCFile(workspace, value)
                }
            }
        }
    }
}