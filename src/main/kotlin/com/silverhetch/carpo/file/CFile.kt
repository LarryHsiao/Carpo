package com.silverhetch.carpo.file

import com.silverhetch.carpo.tag.Tags
import java.io.File

/**
 * Represents a Carpo file.
 */
interface CFile {
    /**
     * File name
     */
    fun title(): String

    /**
     * Thumbnail url
     */
    fun thumbnailUrl(): String

    /**
     * [Tags] instance that attached to this [CFile]
     */
    fun tags(): Tags

    /**
     * Remove this [CFile].
     */
    fun remove()

    /**
     * Rename this [CFile]
     */
    fun rename(newName: String)

    /**
     * @return [CExecutable] object for handling the execution.
     */
    fun executable(): CExecutable

    /**
     * Add files into this [CFile]
     */
    fun addFile(file: List<File>)

    /**
     * The [File] of this [CFile]
     */
    fun jdkFile(): File

    /**
     * The sub files in this [CFile]
     */
    fun subFiles(): Files
}