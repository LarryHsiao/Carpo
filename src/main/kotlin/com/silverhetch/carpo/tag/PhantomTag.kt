package com.silverhetch.carpo.tag

import com.silverhetch.carpo.file.Files
import com.silverhetch.carpo.file.phantom.PhantomFiles

/**
 * Phantom class of [Tag]
 */
class PhantomTag : Tag {
    private var name = "Phantom"
    override fun id(): Long {
        return -1
    }

    override fun title(): String {
        return name
    }

    override fun files(): Files {
        return PhantomFiles()
    }

    override fun rename(newName: String) {
        this.name = newName
    }

    override fun remove() {
        // leave this empty in phantom class
    }
}