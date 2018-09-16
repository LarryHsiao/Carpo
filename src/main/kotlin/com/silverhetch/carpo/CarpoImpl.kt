package com.silverhetch.carpo

import com.silverhetch.carpo.file.CFile
import com.silverhetch.carpo.file.DBFiles
import com.silverhetch.carpo.file.Files
import com.silverhetch.carpo.file.WorkspaceCFile
import com.silverhetch.carpo.tag.DBTags
import com.silverhetch.carpo.tag.Tags
import com.silverhetch.carpo.workspace.Workspace
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
                        dbFileMap[jFile.name] ?: dbFiles.add(jFile.name)
                    )
                }
                return result
            }
        }
        return mapOf()
    }

    override fun tags(): Tags {
        return DBTags(workspace.sqlConn())
    }

    override fun addFile(files: List<File>): CFile {
        if (files.isEmpty()) {
            throw IllegalArgumentException("The files should be at least one.")
        }
        val fileRoot = File(workspace.rootJFile(), files[0].name)

        if (files.size == 1 && files[0].isDirectory) {
            workspace.insertionPipe().through(files[0], File(workspace.rootJFile(), files[0].name))
        } else {
            if (!fileRoot.exists()) {
                fileRoot.mkdir()
            }

            files.forEach { file ->
                workspace.insertionPipe().through(file, File(fileRoot, file.name))
            }
        }
        return all()[fileRoot.name] ?: dbFiles.add(fileRoot.name)
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

    override fun byKeyword(keyword: String): Map<String, CFile> {
        return all().filter {
            it.key.toLowerCase().contains(keyword.toLowerCase())
        }.toMutableMap().also {
            it.putAll(byTag(keyword))
        }
    }
}