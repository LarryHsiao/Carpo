package com.silverhetch.carpo.javafx

import com.jfoenix.controls.JFXListCell
import com.jfoenix.controls.JFXListView
import com.jfoenix.controls.JFXTextField
import com.silverhetch.carpo.model.CFile
import com.silverhetch.carpo.model.Carpo
import com.silverhetch.carpo.model.CarpoImpl
import com.silverhetch.carpo.model.factory.TempCarpoFactory
import com.silverhetch.clotho.log.SystemPrintLog
import com.sun.javafx.collections.ObservableListWrapper
import javafx.beans.value.ChangeListener
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.control.ListCell
import javafx.scene.input.DataFormat
import javafx.scene.input.MouseEvent
import javafx.scene.input.TransferMode
import javafx.scene.layout.VBox
import java.net.URL
import java.util.*
import java.io.File
import javafx.stage.DirectoryChooser
import org.apache.commons.logging.impl.LogFactoryImpl


/**
 * Controls drop zone view.
 */
class DropZone : Initializable {
    @FXML
    private lateinit var dropZone: VBox
    @FXML
    private lateinit var fileList: JFXListView<CFile>
    @FXML
    private lateinit var currentPath: JFXTextField

    private var carpo: Carpo = TempCarpoFactory().fetch()

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        fileList.items = ObservableListWrapper<CFile>(ArrayList<CFile>())
        fileList.setCellFactory { _ ->
            object : JFXListCell<CFile>() {
                override fun updateItem(item: CFile?, empty: Boolean) {
                    super.updateItem(item, empty)
                    if (empty) {
                        text = ""
                    } else {
                        if (item != null) {
                            text = item.title()
                        }

                    }
                }
            }
        }
        fileList.setOnMouseClicked {
            if (it.clickCount > 2) {
                TODO("shows dialog to modify file information")
            }
        }
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
                updateUI()
            }
            event.isDropCompleted = true
            event.consume()
        }
        updateUI()
    }

    @FXML
    private fun changePath(event: MouseEvent) {
        val chooser = DirectoryChooser()
        chooser.title = "JavaFX Projects"
        val defaultDirectory = File(System.getProperty("user.dir"))
        chooser.initialDirectory = defaultDirectory
        chooser.showDialog((event.source as Node).scene.window)?.also {
            carpo = CarpoImpl(it)
            updateUI()
        }
    }

    private fun updateUI() {
        currentPath.text = carpo.workspace().absolutePath
        with(fileList.items) {
            carpo.all().let { list ->
                clear()
                addAll(Array(list.size) {
                    list[it]
                })
            }
        }
    }
}