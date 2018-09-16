package com.silverhetch.carpo.file.comparetor

import com.silverhetch.carpo.file.phantom.PhantomCFile
import com.silverhetch.clotho.utility.comparator.StringComparator
import org.junit.Assert.*
import org.junit.Test

class FileNameComparatorTest {
    @Test
    fun checkInputOrder() {
        FileNameComparator(
            Comparator { name1, name2 ->
                assertEquals("CFile1", name1)
                assertEquals("CFile2", name2)
                0  // has this line for complete this Comparator object.
            }
        ).compare(
            PhantomCFile("CFile1"),
            PhantomCFile("CFile2")
        )
    }
}