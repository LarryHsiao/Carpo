package com.silverhetch.carpo

import com.silverhetch.carpo.file.CFile
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
    fun addFile(file: File): CFile

}