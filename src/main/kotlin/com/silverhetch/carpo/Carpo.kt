package com.silverhetch.carpo

import com.silverhetch.carpo.file.CFile
import com.silverhetch.carpo.tag.Tags
import com.silverhetch.carpo.workspace.Workspace
import java.io.File

/**
 * Main object of Carpo
 */
interface Carpo {
    /**
     * Work space File
     */
    fun workspace(): Workspace

    /**
     * Map all of file that managed by Carpo.
     *
     * @return map key: The file title, map value: the [CFile] instance.
     */
    fun all(): Map<String, CFile>

    /**
     * All records of [Tags] object in Carpo
     */
    fun tags(): Tags

    /**
     * List all of file that has given tag.
     */
    fun byTag(tag: String): Map<String, CFile>

    /**
     * List all of files that has given keyword
     */
    fun byKeyword(keyword: String): Map<String, CFile>

    /**
     * Move given file into Carpo`s workspace.
     */
    fun addFile(files: List<File>): CFile

}