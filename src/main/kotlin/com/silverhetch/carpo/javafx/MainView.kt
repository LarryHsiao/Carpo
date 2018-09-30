package com.silverhetch.carpo.javafx

import com.jfoenix.controls.JFXSnackbar
import com.jfoenix.controls.JFXTextField
import com.silverhetch.carpo.Carpo
import com.silverhetch.carpo.CarpoImpl
import com.silverhetch.carpo.config.CarpoConfigSource
import com.silverhetch.carpo.config.Config
import com.silverhetch.carpo.javafx.file.FileInfoView
import com.silverhetch.carpo.javafx.file.FileListView
import com.silverhetch.carpo.javafx.utility.draging.JdkFileDraging
import com.silverhetch.carpo.javafx.utility.draging.MultiDraging
import com.silverhetch.carpo.workspace.CarpoWorkspace
import com.silverhetch.carpo.workspace.WorkspaceMerging
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.image.ImageView
import javafx.scene.input.DragEvent
import javafx.scene.input.KeyCode
import javafx.scene.input.MouseEvent
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
    @FXML private lateinit var blankZone: ImageView
    @FXML private lateinit var fileListController: FileListView
    @FXML private lateinit var fileInfoController: FileInfoView
    @FXML private lateinit var tagManagementController: TagManagementView
    @FXML private lateinit var snackbar: JFXSnackbar
    private var config: Config = CarpoConfigSource().fetch()
    private var carpo: Carpo = CarpoImpl(
        CarpoWorkspace(
            File(config.workspacePath()).also {
                if (!it.exists()) {
                    it.mkdir()
                }
            }
        )
    )

    override fun initialize(p0: URL?, bundle: ResourceBundle) {
        snackbar = JFXSnackbar(rootPane)

        MultiDraging(
            EventHandler {
                if (it.dragboard.hasFiles().not()) {
                    return@EventHandler
                }
                when (it.eventType) {
                    DragEvent.DRAG_OVER -> {
                        blankZone.isManaged = true
                        blankZone.isVisible = true
                    }
                    else -> {
                        blankZone.isManaged = false
                        blankZone.isVisible = false
                    }
                }
            },
            JdkFileDraging { droppedFiles ->
                carpo.addFile(droppedFiles).also {
                    fileListController.appendCFile(it)
                }
            }
        ).let {
            dropZone.onDragDone = it
            dropZone.onDragDropped = it
            dropZone.onDragExited = it
            dropZone.onDragOver = it
            dropZone.onDragDropped = it
        }

        fileListController.setup(snackbar)
        fileListController.selectionModel().selectedItemProperty().addListener { _, _, selected ->
            selected.also {
                selected?.also { selected ->
                    fileInfoController.loadCFile(selected)
                }
            }
        }
        searchKey.textProperty().addListener { _, _, newValue ->
            fileListController.loadList(carpo.byKeyword(searchKey.text))
        }
        searchKey.sceneProperty().addListener { observable, oldValue, newValue ->
            newValue.setOnKeyPressed {
                if (it.code == KeyCode.F5) {
                    reloadUI()
                }
            }
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

    private fun reloadUI() {
        currentPath.text = carpo.workspace().rootJFile().absolutePath
        fileListController.loadList(
            if (searchKey.text.isBlank()) {
                carpo.all()
            } else {
                carpo.byKeyword(searchKey.text)
            }
        )
        tagManagementController.loadTags(carpo.tags())
    }

    @FXML
    private fun importFrom(mouseEvent: MouseEvent) {
        val chooser = DirectoryChooser()
        chooser.title = ""
        val defaultDirectory = File(System.getProperty("user.dir"))
        chooser.initialDirectory = defaultDirectory
        mouseEvent.source.let { source ->
            if (source is Node) {
                chooser.showDialog((source.scene.window))?.also {
                    WorkspaceMerging(
                        CarpoImpl(
                            CarpoWorkspace(
                                it
                            )
                        ),
                        carpo
                    ).fetch()
                    reloadUI()
                }
            }
        }
    }
}