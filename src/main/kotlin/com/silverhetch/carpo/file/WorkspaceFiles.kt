package com.silverhetch.carpo.file

import com.silverhetch.carpo.workspace.Workspace
import java.io.File

/**
 * [Files] which hybridize the file system(Workspace) and SQLite database.
 *
 * Filter strategy: Returns all the [CFile] instance only if the file is actually exist on File system.
 */
class WorkspaceFiles(private val workspace: Workspace, private val dbFiles: Files) : Files {
    override fun all(): Map<String, CFile> {
        val dbFileMap = dbFiles.all()
        workspace.files().also { jfiles ->
            HashMap<String, CFile>().let { result ->
                jfiles.forEach { jFile ->
                    result[jFile.name] = WorkspaceCFile(
                        workspace,
                        dbFileMap[jFile.name] ?: dbFiles.add(jFile)
                    )
                }
                return result
            }
        }
        return mapOf()
    }

    override fun byTag(tagName: String): Map<String, CFile> {
        return dbFiles.byTag(tagName).filter {
            File(workspace.rootJFile(), it.key).exists()
        }.also { dbMap ->
            return HashMap<String, CFile>().also { result ->
                dbMap.forEach { key, value ->
                    result[key] = WorkspaceCFile(workspace, value)
                }
            }
        }
    }

    override fun add(file: File): CFile {
        workspace.insertionPipe().through(file, File(workspace.rootJFile(), file.name))
        return all()[file.name] ?: WorkspaceCFile(workspace, dbFiles.add(file))
    }
}