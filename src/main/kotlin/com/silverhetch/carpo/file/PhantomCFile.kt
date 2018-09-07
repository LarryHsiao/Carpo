package com.silverhetch.carpo.file

import com.silverhetch.carpo.tag.PhantomTags
import com.silverhetch.carpo.tag.Tags

/**
 * Phantom class of [CFile]
 */
class PhantomCFile(private val title: String = "Phantom") : CFile {
    override fun title(): String {
        return title
    }

    override fun tags(): Tags {
        return PhantomTags()
    }

    override fun remove() {
        // leave empty in phantom class
    }
}