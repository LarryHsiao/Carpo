package com.silverhetch.carpo.file

import com.silverhetch.carpo.tag.Tags
import com.silverhetch.carpo.tag.WorkspaceTags
import com.silverhetch.carpo.util.FileImageUrl
import com.silverhetch.carpo.workspace.Workspace
import java.io.File

/**
 * Decorator that operates changes to file system.
 */
class WorkspaceCFile(private val workspace: Workspace, private val dbcFile: CFile) : CFile {
    override fun title(): String {
        return dbcFile.title()
    }

    override fun thumbnailUrl(): String {
        return FileImageUrl(jdkFile(), javaClass.getResource("/icon/file.svg").toString()).value()
    }

    override fun tags(): Tags {
        return WorkspaceTags(workspace, dbcFile.tags())
    }

    override fun remove() {
        jdkFile().deleteRecursively()
        dbcFile.remove()
    }

    override fun rename(newName: String) {
        File(workspace.rootJFile(), newName).let { target ->
            if (jdkFile().renameTo(target)) {
                dbcFile.rename(newName)
            }
        }
    }

    override fun executable(): CExecutable {
        return FileExecutable(jdkFile().toURI().toString())
    }

    override fun addFile(file: List<File>) {
        file.forEach {
            workspace.insertionPipe().through(it, File(jdkFile(), it.name))
        }
    }

    override fun jdkFile(): File {
        return File(workspace.rootJFile(), dbcFile.title())
    }

    override fun subFiles(): Files {
        return SubFiles(workspace, this)
    }
}