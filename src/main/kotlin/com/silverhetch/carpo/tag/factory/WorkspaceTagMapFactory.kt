package com.silverhetch.carpo.tag.factory

import com.silverhetch.carpo.tag.Tag
import com.silverhetch.carpo.tag.WorkspaceTag
import com.silverhetch.carpo.workspace.Workspace
import com.silverhetch.clotho.Source

/**
 * Tag map factory that wrap the given [Tag] instance with [WorkspaceTag] to hybridize the file system and database .
 */
class WorkspaceTagMapFactory(private val workspace: Workspace, private val dbTagMap: Map<String, Tag>) : Source<Map<String, Tag>> {
    override fun value(): Map<String, Tag> {
        return HashMap<String, Tag>().also { result ->
            dbTagMap.forEach { title, tag ->
                result[title] = WorkspaceTag(workspace, tag)
            }
        }
    }
}