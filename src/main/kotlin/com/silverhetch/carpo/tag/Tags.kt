package com.silverhetch.carpo.tag

/**
 * Represent tags.
 */
interface Tags {
    /**
     * All the tags.
     */
    fun all(): List<Tag>

    /**
     * New tags.
     */
    fun addTag(name: String)
}