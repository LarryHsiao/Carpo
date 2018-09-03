package com.silverhetch.carpo.model

/**
 * Represents a Carpo file.
 */
interface CFile {
    /**
     * File name
     */
    fun title(): String

    /**
     * Tags which this file have
     */
    fun tags(): List<String>

    /**
     * Add a tag.
     */
    fun addTag(tag: String)

    /**
     * Remove a tag which is exactly as same as parameter.
     */
    fun removeTag(tag: String)
}