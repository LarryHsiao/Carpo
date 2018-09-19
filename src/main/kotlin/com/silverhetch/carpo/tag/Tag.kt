package com.silverhetch.carpo.tag

import com.silverhetch.carpo.file.Files

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

    /**
     * Rename this tag with given name.
     */
    fun rename(newName: String)

    /**
     * [Files] hasthis [Tag]
     */
    fun files(): Files

    /**
     * Remove this tag
     */
    fun remove()
}