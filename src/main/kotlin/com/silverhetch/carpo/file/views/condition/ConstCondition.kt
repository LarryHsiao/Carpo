package com.silverhetch.carpo.file.views.condition

/**
 * Represent a condition for view.
 */
class ConstCondition(private val id: Long,
                     private val tagId: Long,
                     private val viewId: Long,
                     private val type: String) : Condition {
    override fun id(): Long {
        return id
    }

    override fun tagId(): Long {
        return tagId
    }

    override fun viewId(): Long {
        return viewId
    }

    override fun type(): Condition.Type {
        return Condition.Type.valueOf(type)
    }
}