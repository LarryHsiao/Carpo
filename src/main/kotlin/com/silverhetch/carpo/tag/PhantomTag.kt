package com.silverhetch.carpo.tag

class PhantomTag : Tag {
    override fun id(): Long {
        return -1
    }

    override fun title(): String {
        return "Phantom"
    }
}