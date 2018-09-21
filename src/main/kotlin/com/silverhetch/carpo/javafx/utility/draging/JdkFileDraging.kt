package com.silverhetch.carpo.javafx.utility.draging

import javafx.event.EventHandler
import javafx.scene.input.DataFormat
import javafx.scene.input.DragEvent
import javafx.scene.input.DragEvent.DRAG_DROPPED
import javafx.scene.input.DragEvent.DRAG_OVER
import javafx.scene.input.TransferMode
import java.io.File

class JdkFileDraging(private val droppedFile: (files: List<File>) -> Unit) : EventHandler<DragEvent> {
    override fun handle(event: DragEvent) {
        when (event.eventType) {
            DRAG_OVER -> {
                if (event.dragboard.hasContent(DataFormat.FILES)) {
                    event.acceptTransferModes(TransferMode.MOVE)
                }
            }
            DRAG_DROPPED -> {
                if (event.dragboard.hasContent(DataFormat.FILES)) {
                    droppedFile(event.dragboard.files)
                    event.isDropCompleted = true
                    event.consume()
                }
            }
        }
    }
}