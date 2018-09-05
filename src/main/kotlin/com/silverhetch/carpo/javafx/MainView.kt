package com.silverhetch.carpo.javafx

import com.jfoenix.controls.JFXListCell
import com.jfoenix.controls.JFXListView
import com.jfoenix.controls.JFXTextField
import com.silverhetch.carpo.Carpo
import com.silverhetch.carpo.CarpoImpl
import com.silverhetch.carpo.file.CFile
import com.sun.javafx.collections.ObservableListWrapper
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.input.DataFormat
import javafx.scene.input.MouseEvent
import javafx.scene.input.TransferMode
import javafx.scene.layout.VBox
import javafx.stage.DirectoryChooser
import java.io.File
import java.net.URL
import java.util.*


/**
 * Controls drop zone view.
 */
class MainView : Initializable {
    @FXML
    private lateinit var dropZone: VBox
    @FXML
    private lateinit var fileList: JFXListView<CFile>
    @FXML
    private lateinit var currentPath: JFXTextField
    @FXML
    private lateinit var fileInfoController: FileInfoView

    private var carpo: Carpo = CarpoImpl(File(System.getProperty("user.dir")))


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
            fileInfoController.loadCFile(fileList.selectionModel.selectedItem)
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