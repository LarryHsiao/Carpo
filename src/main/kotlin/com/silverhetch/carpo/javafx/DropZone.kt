package com.silverhetch.carpo.javafx

import com.silverhetch.carpo.model.factory.TempCarpoFactory
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import javafx.scene.input.DataFormat
import javafx.scene.input.TransferMode
import javafx.scene.layout.AnchorPane
import java.net.URL
import java.util.*

class DropZone : Initializable {
    @FXML
    private lateinit var dropZone: AnchorPane
    @FXML
    private lateinit var info: Label

    private val carpo = TempCarpoFactory().fetch()

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        dropZone.setOnDragOver {
            if (it.dragboard.hasContent(DataFormat.FILES)) {
                it.acceptTransferModes(*TransferMode.COPY_OR_MOVE)
            }
            it.consume()
        }
        dropZone.setOnDragDropped { event ->
            if (event.dragboard.hasContent(DataFormat.FILES)) {
                event.dragboard.files.forEach { file ->
                    carpo.addFile(file)
                }
                info.text = StringBuilder().also { stringBuilder ->
                    carpo.all().forEach { file ->
                        stringBuilder.appendln(file.title())
                    }
                }.toString()
            }
            event.isDropCompleted = true
            event.consume()
        }
    }
}