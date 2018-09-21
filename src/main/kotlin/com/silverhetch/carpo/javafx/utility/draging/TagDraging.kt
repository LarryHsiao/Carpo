package com.silverhetch.carpo.javafx.utility.draging

import com.silverhetch.carpo.tag.factory.UriTagFactory
import javafx.event.EventHandler
import javafx.scene.input.DragEvent
import javafx.scene.input.DragEvent.DRAG_DROPPED
import javafx.scene.input.DragEvent.DRAG_OVER
import javafx.scene.input.TransferMode.LINK

/**
 * Handling [DragEvent] that have [Tag] url in dragboard.
 */
class TagDraging(private val dropped: (tagName: String) -> Unit) : EventHandler<DragEvent> {
    override fun handle(event: DragEvent) {
        when (event.eventType) {
            DRAG_OVER -> {
                if (event.dragboard.hasUrl() && UriTagFactory().isValidUri(event.dragboard.url)) {
                    event.acceptTransferModes(LINK)
                    event.consume()
                }
            }
            DRAG_DROPPED -> {
                if (event.dragboard.hasUrl() && UriTagFactory().isValidUri(event.dragboard.url)) {
                    dropped(UriTagFactory.TagName(event.dragboard.url).fetch())
                    event.isDropCompleted = true
                    event.consume()
                }
            }
        }
    }
}