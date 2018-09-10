package com.silverhetch.carpo.file

import com.silverhetch.carpo.Workspace
import com.silverhetch.carpo.tag.Tags
import java.io.File

/**
 * Decorator that operates changes to file system.
 */
class WorkspaceCFile(private val workspace: Workspace, private val dbcFile: CFile) : CFile {
    override fun title(): String {
        return dbcFile.title()
    }

    override fun tags(): Tags {
        return dbcFile.tags()
    }

    override fun remove() {
        jdkFile().deleteRecursively()
        dbcFile.remove()
    }

    override fun executable(): CExecutable {
        return FileExecutable(jdkFile().toURI().toString())
    }

    private fun jdkFile(): File {
        return File(workspace.rootJFile(), dbcFile.title())
    }
}