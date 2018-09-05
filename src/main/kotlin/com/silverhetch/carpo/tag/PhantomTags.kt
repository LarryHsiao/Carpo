package com.silverhetch.carpo.tag

/**
 * Phantom tags which do nothing.
 */
class PhantomTags : Tags {
    override fun all(): List<Tag> {
        return listOf()
    }

    override fun addTag(name: String) {
    }
}