package com.silverhetch.carpo.file

import com.silverhetch.carpo.file.factory.WorkspaceMapFactory
import com.silverhetch.carpo.workspace.Workspace
import java.io.File

/**
 * A decorator for filter [CFile] that does not have the given tag.
 */
class WorkspaceTaggedFiles(private val workspace: Workspace, private val files: Files) : Files {
    override fun all(): Map<String, CFile> {
        return WorkspaceMapFactory(
            workspace,
            files.all()
        ).fetch()
    }

    override fun byTag(tagName: String): Map<String, CFile> {
        return WorkspaceMapFactory(
            workspace,
            files.byTag(tagName)
        ).fetch()
    }

    override fun add(file: File): CFile {
        workspace.insertionPipe().through(file, File(workspace.rootJFile(), file.name))
        return WorkspaceCFile(workspace, files.add(file))
    }
}