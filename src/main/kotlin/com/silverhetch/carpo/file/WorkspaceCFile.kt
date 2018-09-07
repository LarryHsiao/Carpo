package com.silverhetch.carpo.file

import com.silverhetch.carpo.Workspace
import com.silverhetch.carpo.tag.Tags
import java.io.File

/**
 * [CFile] that implemented with database and file system.
 */
class WorkspaceCFile(private val workspace: Workspace, private val dbcFile: CFile) : CFile {
    override fun title(): String {
        return dbcFile.title()
    }

    override fun tags(): Tags {
        return dbcFile.tags()
    }

    override fun remove() {
        File(workspace.rootJFile(), dbcFile.title()).delete()
        dbcFile.remove()
    }
}