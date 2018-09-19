package com.silverhetch.carpo.tag

import com.silverhetch.carpo.file.DBTagFiles
import com.silverhetch.carpo.file.Files
import com.silverhetch.carpo.file.WorkspaceFiles
import com.silverhetch.carpo.file.WorkspaceTaggedFiles
import com.silverhetch.carpo.workspace.Workspace

class WorkspaceTag(private val workspace: Workspace, private val dbTag: Tag) : Tag {
    override fun id(): Long {
        return dbTag.id()
    }

    override fun title(): String {
        return dbTag.title()
    }

    override fun files(): Files {
        return WorkspaceTaggedFiles(
            workspace,
            DBTagFiles(workspace.sqlConn(), id())
        )
    }

    override fun rename(newName: String) {
        dbTag.rename(newName)
    }

    override fun remove() {
        dbTag.remove()
    }
}