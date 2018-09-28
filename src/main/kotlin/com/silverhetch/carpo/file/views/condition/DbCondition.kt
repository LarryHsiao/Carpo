package com.silverhetch.carpo.file.views.condition

import java.sql.Connection

class DbCondition(private val connection: Connection,
                  private val id: Long,
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