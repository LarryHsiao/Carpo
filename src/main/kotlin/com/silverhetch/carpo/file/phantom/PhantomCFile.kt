package com.silverhetch.carpo.file.phantom

import com.silverhetch.carpo.file.CExecutable
import com.silverhetch.carpo.file.CFile
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

    override fun executable(): CExecutable {
        return PhantomExecutable()
    }
}