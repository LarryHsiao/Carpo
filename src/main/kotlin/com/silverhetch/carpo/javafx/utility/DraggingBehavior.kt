package com.silverhetch.carpo.javafx.utility

import javafx.event.EventHandler
import javafx.scene.input.DataFormat
import javafx.scene.input.DragEvent
import javafx.scene.input.DragEvent.*
import javafx.scene.input.TransferMode
import java.io.File

class DraggingBehavior(private val events: Events) : EventHandler<DragEvent> {
    interface Events {
        fun onDroppedFiles(files: List<File>)
    }

    override fun handle(event: DragEvent) {
        when (event.eventType) {
            DRAG_OVER -> {
                if (event.dragboard.hasContent(DataFormat.FILES)) {
                    event.acceptTransferModes(TransferMode.COPY, TransferMode.MOVE)
                }
                event.consume()
            }
            DRAG_DROPPED -> {
                if (event.dragboard.hasContent(DataFormat.FILES)) {
                    events.onDroppedFiles(event.dragboard.files)
                }
                event.isDropCompleted = true
                event.consume()
            }
        }
    }
}