package com.silverhetch.carpo.file

import com.silverhetch.carpo.tag.Tags

/**
 * Represents a Carpo file.
 */
interface CFile {
    /**
     * File name
     */
    fun title(): String

    /**
     * [Tags] instance that attached to this [CFile]
     */
    fun tags(): Tags

    /**
     * Remove this tag.
     */
    fun remove()

    /**
     * @return [CExecutable] object for handling the execution.
     */
    fun executable(): CExecutable
}