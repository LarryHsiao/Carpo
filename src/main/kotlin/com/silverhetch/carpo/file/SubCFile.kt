package com.silverhetch.carpo.file

import com.silverhetch.carpo.tag.Tags
import com.silverhetch.carpo.util.FileImageUrl
import com.silverhetch.carpo.workspace.Workspace
import java.io.File

/**
 * Class that represents the sub file of [CFile].
 */
class SubCFile(private val workspace: Workspace, private var jdkFile: File, private val root: CFile) : CFile {
    override fun title(): String {
        return jdkFile.name
    }

    override fun thumbnailUrl(): String {
        return FileImageUrl(jdkFile(), javaClass.getResource("/icon/file.svg").toString()).fetch()
    }

    override fun tags(): Tags {
        return root.tags()
    }

    override fun remove() {
        jdkFile.deleteRecursively()
    }

    override fun rename(newName: String) {
        File(jdkFile.parentFile, newName).let { target ->
            if (jdkFile.renameTo(target)) {
                jdkFile = target
            }
        }
    }

    override fun executable(): CExecutable {
        return FileExecutable(jdkFile.toURI().toString())
    }

    override fun addFile(file: List<File>) {
        if (jdkFile.isDirectory) {
            file.forEach { newFile ->
                workspace.insertionPipe().through(newFile, File(jdkFile, newFile.name))
            }
        } else {
            root.addFile(file)
        }
    }

    override fun jdkFile(): File {
        return jdkFile
    }

    override fun subFiles(): Files {
        return SubFiles(workspace, root, jdkFile)
    }
}