package com.silverhetch.carpo.tag

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
}