package com.silverhetch.carpo.tag

/**
 * Represent tags.
 */
interface Tags {
    /**
     * All the tags.
     */
    fun all(): Map<String, Tag>

    /**
     * New tags.
     */
    fun addTag(name: String): Tag
}