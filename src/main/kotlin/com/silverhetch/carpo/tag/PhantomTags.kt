package com.silverhetch.carpo.tag

/**
 * Phantom class of [Tags]
 */
class PhantomTags : Tags {
    override fun all(): Map<String, Tag> {
        return mapOf()
    }

    override fun addTag(name: String): Tag {
        return PhantomTag()
    }

    override fun byName(name: String): Map<String, Tag> {
        return mapOf()
    }
}