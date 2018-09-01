package com.silverhetch.carpo.model

import java.io.File

class CarpoImpl(private val workspace: File) : Carpo {
    override fun all(): List<CFile> {
        workspace.listFiles()?.also { files ->
            return MutableList(files.size) {
                ConstCFile(files[it])
            }
        }
        return listOf()
    }
}