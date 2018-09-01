package com.silverhetch.carpo.model

import java.io.File

class ConstCFile(private val file: File) : CFile {
    override fun title(): String {
        return file.name
    }
}