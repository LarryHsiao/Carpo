package com.silverhetch.carpo.file.views.condition

/**
 * Condition
 */
interface Condition {
    /**
     * Id of this condition
     */
    fun id(): Long

    /**
     * [Tag] id
     */
    fun tagId(): Long

    /**
     * [View] id
     */
    fun viewId(): Long

    /**
     * The condition type
     */
    fun type(): Type

    /**
     * The enum of condition type
     */
    enum class Type {
        INCLUDED,
        EXCLUDED
    }
}