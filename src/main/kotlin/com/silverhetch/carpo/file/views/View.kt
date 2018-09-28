package com.silverhetch.carpo.file.views

import com.silverhetch.carpo.file.views.condition.Conditions

/**
 * Represent [View] that
 */
interface View {
    /**
     * Id of this View
     */
    fun id(): Long

    /**
     * Name of this View
     */
    fun name(): String

    /**
     * Remove this [View]
     */
    fun remove()

    /**
     * [Conditions] of this [View]
     */
    fun conditions(): Conditions
}