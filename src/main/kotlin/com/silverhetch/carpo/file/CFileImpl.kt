package com.silverhetch.carpo.file

import com.silverhetch.carpo.tag.Tags
import java.io.File

class CFileImpl(private val file: File, private val dbcFile: DBCFile) : CFile {
    override fun title(): String {
        return file.name
    }

    override fun tags(): Tags {
        return dbcFile.tags()
    }

    override fun remove() {
        file.delete()
        dbcFile.remove()
    }
}