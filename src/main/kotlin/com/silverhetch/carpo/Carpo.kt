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
    fun workspace(): File

    /**
     * List all of file that managed by Carpo.
     */
    fun all(): List<CFile>

    /**
     * List all of file that has given tag.
     */
    fun byTag(tag: String): List<CFile>

    /**
     * Move given file into Carpo`s workspace.
     */
    fun addFile(file: File)

}