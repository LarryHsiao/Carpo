package com.silverhetch.carpo.tag

import java.util.*

/**
 * Comparator decorator which compares [Tag] in Name.
 */
class TagNameComparator(private val comparator: Comparator<String>) : Comparator<Tag> {
    override fun compare(o1: Tag, o2: Tag): Int {
        return comparator.compare(o1.title(), o2.title())
    }
}