package com.silverhetch.carpo.file

import com.silverhetch.carpo.workspace.Workspace
import java.io.File

/**
 * Sub files in the [CFile]
 *
 * @param jdkFile Parent [File] on File system. Which may be second layer from [root].
 * @param root The root [CFile] of this [SubFiles].
 */
class SubFiles(private val workspace: Workspace, private val root: CFile, private val jdkFile: File) : Files {

    /**
     * Constructor that construct the first layer [SubFiles] of the [CFile]
     */
    constructor(workspace: Workspace, rootCFile: CFile) : this(workspace, rootCFile, rootCFile.jdkFile())

    override fun all(): Map<String, CFile> {
        return HashMap<String, CFile>().also { map ->
            jdkFile.listFiles()?.also {
                it.forEach { subJdkFile ->
                    map[subJdkFile.name] = SubCFile(workspace, subJdkFile, root)
                }
            }
        }
    }

    override fun byTag(tagName: String): Map<String, CFile> {
        /**
         * Tag is apply to entire [CFile] which means all of sub file is applied.
         */
        return if (root.tags().byName(tagName).isEmpty()) {
            mapOf()
        } else {
            all()
        }
    }

    override fun add(file: File): CFile {
        File(jdkFile, file.name).let { target ->
            workspace.insertionPipe().through(file, target)
            return SubCFile(workspace, target, root)
        }
    }
}