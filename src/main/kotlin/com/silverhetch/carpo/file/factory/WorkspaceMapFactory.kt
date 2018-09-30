package com.silverhetch.carpo.file.factory

import com.silverhetch.carpo.file.CFile
import com.silverhetch.carpo.file.WorkspaceCFile
import com.silverhetch.carpo.workspace.Workspace
import com.silverhetch.clotho.Source
import java.io.File

/**
 * Map source that filter out [CFile] which exist in workspace.
 */
class WorkspaceMapFactory(private val workspace: Workspace, private val dbMap: Map<String, CFile>) : Source<Map<String, CFile>> {
    override fun fetch(): Map<String, CFile> {
        return dbMap.filterKeys { key ->
            File(workspace.rootJFile(), key).exists()
        }.let { dbMap ->
            HashMap<String, CFile>().also {
                dbMap.forEach { key, cfile ->
                    it[key] = WorkspaceCFile(workspace, cfile)
                }
            }
        }
    }
}