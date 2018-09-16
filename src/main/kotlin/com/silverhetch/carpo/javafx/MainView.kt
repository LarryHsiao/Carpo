package com.silverhetch.carpo.javafx

import com.jfoenix.controls.JFXSnackbar
import com.jfoenix.controls.JFXTextField
import com.silverhetch.carpo.Carpo
import com.silverhetch.carpo.CarpoImpl
import com.silverhetch.carpo.workspace.CarpoWorkspace
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.input.*
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.DirectoryChooser
import java.io.File
import java.net.URL
import java.util.*


/**
 * Controls drop zone view.
 */
class MainView : Initializable {
    @FXML private lateinit var rootPane: HBox
    @FXML private lateinit var dropZone: VBox
    @FXML private lateinit var currentPath: JFXTextField
    @FXML private lateinit var searchKey: JFXTextField
    @FXML private lateinit var tagListController: TagListView
    @FXML private lateinit var fileListController: FileListView
    @FXML private lateinit var fileInfoController: FileInfoView
    @FXML private lateinit var snackbar: JFXSnackbar

    private var carpo: Carpo = CarpoImpl(
        CarpoWorkspace(
            File(System.getProperty("user.home") + "/Playground").also {
                if (it.exists().not()) {
                    it.mkdir()
                }
                File(it, "Sample file").also { sampleFile ->
                    if (sampleFile.exists().not()) {
                        sampleFile.createNewFile()
                    }
                }
            }
        )
    )


    override fun initialize(p0: URL?, bundle: ResourceBundle?) {
        snackbar = JFXSnackbar(rootPane)
        fileListController.setup(snackbar)
        fileListController.selectionModel().selectedItemProperty().addListener { _, _, selected ->
            selected.also {
                selected?.also { selected ->
                    fileInfoController.loadCFile(selected)
                }
            }
        }
        dropZone.setOnDragOver {
            if (it.dragboard.hasContent(DataFormat.FILES)) {
                it.acceptTransferModes(TransferMode.COPY, TransferMode.MOVE)
            }
            it.consume()
        }
        dropZone.setOnDragDropped { event ->
            if (event.dragboard.hasContent(DataFormat.FILES)) {
                carpo.addFile(event.dragboard.files).also {
                    fileListController.appendCFile(it)
                }
            }
            event.isDropCompleted = true
            event.consume()
        }
        reloadUI()
    }

    @FXML
    private fun changePath(event: MouseEvent) {
        val chooser = DirectoryChooser()
        chooser.title = ""
        val defaultDirectory = File(System.getProperty("user.dir"))
        chooser.initialDirectory = defaultDirectory
        event.source.let { source ->
            if (source is Node) {
                chooser.showDialog((source.scene.window))?.also {
                    carpo = CarpoImpl(
                        CarpoWorkspace(
                            it
                        )
                    )
                    reloadUI()
                }
            }
        }
    }

    @FXML
    private fun searchByKey() {
        fileListController.loadList(carpo.byKeyword(searchKey.text))
    }

    private fun reloadUI() {
        currentPath.text = carpo.workspace().rootJFile().absolutePath
        fileListController.loadList(carpo.all())
        tagListController.loadTags(carpo.tags().all())
    }

    @FXML
    private fun searchKeyFieldKeyPressed(keyEvent: KeyEvent) {
        if (keyEvent.code == KeyCode.ENTER) {
            searchByKey()
        }
    }
}