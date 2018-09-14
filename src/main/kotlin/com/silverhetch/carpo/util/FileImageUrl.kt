package com.silverhetch.carpo.util

import com.silverhetch.clotho.Source
import java.io.File
import javax.activation.MimetypesFileTypeMap

/**
 * Source that find Image url with given file.
 *
 * Determine if given is exactly image or image file url in the directory(subdirectory not included)
 *
 */
class FileImageUrl(private val file: File, private val defaultUrl: String) : Source<String> {
    private val mimeTypeMap = MimetypesFileTypeMap()
    override fun fetch(): String {
        if (file.isFile) {
            return if (mimeTypeMap.getContentType(file).contains("image")) {
                file.toURI().toString()
            } else {
                defaultUrl
            }
        } else {
            file.listFiles().forEach {
                if (mimeTypeMap.getContentType(it).contains("image")) {
                    return it.toURI().toString()
                }
            }
            return defaultUrl
        }
    }
}