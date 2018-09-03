package com.silverhetch.carpo.model

import java.io.File

/**
 * Main object of Carpo
 */
interface Carpo {
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