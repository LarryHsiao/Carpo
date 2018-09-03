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

    override fun addFile(file: File) {
        file.renameTo(File(workspace, file.name))
    }

    override fun byTag(tag: String): List<CFile> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}