package com.silverhetch.carpo.javafx.utility.draging

import javafx.event.EventHandler
import javafx.scene.input.DragEvent

/**
 * Handling multiple instance of [EventHandler] into one.
 */
class MultiDraging(private vararg val eventHandlers: EventHandler<DragEvent>) : EventHandler<DragEvent> {
    override fun handle(event: DragEvent?) {
        event?.also {
            eventHandlers.forEach { handler ->
                handler.handle(event)
                if (event.isConsumed) {
                    return@forEach
                }
            }
        }
    }
}