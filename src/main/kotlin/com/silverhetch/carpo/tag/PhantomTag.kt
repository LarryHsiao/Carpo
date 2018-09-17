package com.silverhetch.carpo.tag

import com.silverhetch.carpo.file.Files
import com.silverhetch.carpo.file.phantom.PhantomFiles

/**
 * Phantom class of [Tag]
 */
class PhantomTag : Tag {
    override fun id(): Long {
        return -1
    }

    override fun title(): String {
        return "Phantom"
    }

    override fun files(): Files {
        return PhantomFiles()
    }
}