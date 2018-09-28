package com.silverhetch.carpo.file.views.condition

import com.silverhetch.carpo.tag.Tag

/**
 * Conditions
 */
interface Conditions {
    /**
     * All of the conditions
     */
    fun all(): List<Condition>

    /**
     * New condition
     */
    fun newCondition(tag: Tag, type: Condition.Type): Condition
}