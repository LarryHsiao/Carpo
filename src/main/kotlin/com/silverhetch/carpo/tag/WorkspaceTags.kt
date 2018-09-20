package com.silverhetch.carpo.tag

import com.silverhetch.carpo.tag.factory.WorkspaceTagMapFactory
import com.silverhetch.carpo.workspace.Workspace

/**
 * Decorator that hybridize the file system and Database.
 */
class WorkspaceTags(private val workspace: Workspace, private val dbTags: Tags) : Tags {
    override fun all(): Map<String, Tag> {
        return WorkspaceTagMapFactory(workspace, dbTags.all()).fetch()
    }

    override fun addTag(name: String): Tag {
        return WorkspaceTag(workspace, dbTags.addTag(name))
    }

    override fun byName(name: String): Map<String, Tag> {
        return WorkspaceTagMapFactory(workspace, dbTags.byName(name)).fetch()
    }
}