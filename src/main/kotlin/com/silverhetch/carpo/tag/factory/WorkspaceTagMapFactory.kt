package com.silverhetch.carpo.tag.factory

import com.silverhetch.carpo.tag.Tag
import com.silverhetch.carpo.tag.WorkspaceTag
import com.silverhetch.carpo.workspace.Workspace
import com.silverhetch.clotho.Source

class WorkspaceTagMapFactory(private val workspace: Workspace, private val dbTagMap: Map<String, Tag>) : Source<Map<String, Tag>> {
    override fun fetch(): Map<String, Tag> {
        return HashMap<String, Tag>().also { result ->
            dbTagMap.forEach { title, tag ->
                result[title] = WorkspaceTag(workspace, tag)
            }
        }
    }
}