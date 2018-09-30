package com.silverhetch.carpo.workspace

import com.silverhetch.carpo.Carpo
import com.silverhetch.clotho.Source
import java.io.File

/**
 * Merges two workspace.
 */
class WorkspaceMerging(private val from: Carpo, private val dist: Carpo) : Source<Unit> {
    override fun fetch() {
        from.all().forEach { filename, CFile ->
            CFile.tags().all().let {
                dist.addFile(listOf(File(from.workspace().rootJFile(), filename))).also { distCFile ->
                    it.forEach { importTagName, _ ->
                        distCFile.tags().addTag(importTagName)
                    }
                }
            }
        }
    }
}