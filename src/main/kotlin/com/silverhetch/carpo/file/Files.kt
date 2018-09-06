package com.silverhetch.carpo.file

/**
 * Represent files
 */
interface Files {
    /**
     * All files that managed by this instance.
     */
    fun all(): Map<String, CFile>

    /**
     * All files that managed by this instance with given tag.
     */
    fun byTag(tagName: String): Map<String, CFile>

    /**
     * New file with given name.
     */
    fun add(fileName: String): CFile
}