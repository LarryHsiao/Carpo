package com.silverhetch.carpo.javafx

import com.jfoenix.controls.JFXSnackbar
import com.jfoenix.controls.JFXTabPane
import com.jfoenix.controls.JFXTextField
import com.silverhetch.carpo.Carpo
import com.silverhetch.carpo.CarpoImpl
import com.silverhetch.carpo.javafx.utility.draging.JdkFileDraging
import com.silverhetch.carpo.javafx.utility.draging.MultiDraging
import com.silverhetch.carpo.workspace.CarpoWorkspace
import com.silverhetch.carpo.workspace.DefaultWorkspaceFile
import com.sun.javafx.css.StyleManager
import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory
import javafx.application.Application
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.DragEvent
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.DirectoryChooser
import javafx.stage.Stage
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
    @FXML private lateinit var tagListController: TagListView
    @FXML private lateinit var fileListController: FileListView
    @FXML private lateinit var fileInfoController: FileInfoView
    @FXML private lateinit var tagManagementController: TagManagementView
    @FXML private lateinit var snackbar: JFXSnackbar
    private var carpo: Carpo = CarpoImpl(
        CarpoWorkspace(
            DefaultWorkspaceFile().fetch().also {
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

    init {
        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA)
        StyleManager.getInstance().addUserAgentStylesheet(
            javaClass.getResource("/ui/css/General.css").toURI().toString()
        )
        SvgImageLoaderFactory.install()
    }

    override fun initialize(p0: URL?, bundle: ResourceBundle) {
        Platform.runLater {
            (rootPane.scene.window as Stage).icons.add(
                Image(javaClass.getResource("/ui/icon/alpha-c-box.svg").toString())
            )
        }
        snackbar = JFXSnackbar(rootPane)

        MultiDraging(
            EventHandler {
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
        tagListController.loadTags(carpo.tags().byName(searchKey.text))
        fileListController.loadList(carpo.byKeyword(searchKey.text))
    }

    private fun reloadUI() {
        currentPath.text = carpo.workspace().rootJFile().absolutePath
        fileListController.loadList(carpo.all())
        tagListController.loadTags(carpo.tags().all())
        tagManagementController.loadTags(carpo.tags())
    }

    @FXML
    private fun searchKeyFieldKeyPressed(keyEvent: KeyEvent) {
        if (keyEvent.code == KeyCode.ENTER) {
            searchByKey()
        }
    }
}