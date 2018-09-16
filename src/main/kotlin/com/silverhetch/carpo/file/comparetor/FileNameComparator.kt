package com.silverhetch.carpo.file.comparetor

import com.silverhetch.carpo.file.CFile
import java.util.*

/**
 * Comparator decorator which compare [CFile] in titles.
 */
class FileNameComparator(private val comparator: Comparator<String>) : Comparator<CFile> {
    override fun compare(o1: CFile, o2: CFile): Int {
        return comparator.compare(o1.title(), o2.title())
    }
}