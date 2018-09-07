package com.silverhetch.carpo.tag

/**
 * Represent an tag
 */
interface Tag {
    /**
     * Id of this tag
     */
    fun id(): Long

    /**
     * Title of this tag.
     */
    fun title(): String
}