package com.silverhetch.carpo

import com.silverhetch.carpo.file.CFile
import com.silverhetch.carpo.file.DBFiles
import com.silverhetch.carpo.file.Files
import com.silverhetch.carpo.file.WorkspaceFiles
import com.silverhetch.carpo.tag.DBTags
import com.silverhetch.carpo.tag.Tags
import com.silverhetch.carpo.tag.WorkspaceTags
import com.silverhetch.carpo.workspace.Workspace
import java.io.File

/**
 * Main logic implementation of Carpo.
 */
class CarpoImpl(private val workspace: Workspace) : Carpo {
    private val workspaceFiles: Files

    init {
        this.workspaceFiles = WorkspaceFiles(workspace, DBFiles(workspace.sqlConn()))
    }

    override fun workspace(): Workspace {
        return workspace
    }

    override fun all(): Map<String, CFile> {
        return workspaceFiles.all()
    }

    override fun tags(): Tags {
        return WorkspaceTags(
            workspace,
            DBTags(workspace.sqlConn())
        )
    }

    override fun addFile(files: List<File>): CFile {
        if (files.isEmpty()) {
            throw IllegalArgumentException("The files should be at least one.")
        }

        val fileRoot = File(
            java.nio.file.Files.createTempDirectory(files[0].parentFile.toPath(), ".carpo_temp").toFile().also {
                it.deleteOnExit()
            },
            files[0].name
        )

        if (files.size == 1 && files[0].isDirectory) {
            return workspaceFiles.add(files[0])
        } else {

            if (!fileRoot.exists()) {
                fileRoot.mkdir()
            }

            files.forEach { file ->
                workspace.insertionPipe().through(file, File(fileRoot, file.name))
            }
        }
        return workspaceFiles.add(fileRoot)
    }

    override fun byTag(tag: String): Map<String, CFile> {
        return workspaceFiles.byTag(tag)
    }

    override fun byKeyword(keyword: String): Map<String, CFile> {
        return all().filter {
            it.key.toLowerCase().contains(keyword.toLowerCase())
        }.toMutableMap().also {
            it.putAll(byTag(keyword))
        }
    }
}