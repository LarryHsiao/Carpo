package com.silverhetch.carpo.javafx

import com.jfoenix.controls.JFXListCell
import com.jfoenix.controls.JFXListView
import com.jfoenix.controls.JFXSnackbar
import com.jfoenix.controls.JFXTextField
import com.silverhetch.carpo.Carpo
import com.silverhetch.carpo.CarpoImpl
import com.silverhetch.carpo.workspace.CarpoWorkspace
import com.silverhetch.carpo.file.CExecutable
import com.silverhetch.carpo.file.CFile
import com.sun.javafx.collections.ObservableListWrapper
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.scene.control.TextInputDialog
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
    @FXML
    private lateinit var rootPane: HBox
    @FXML
    private lateinit var dropZone: VBox
    @FXML
    private lateinit var fileList: JFXListView<CFile>
    @FXML
    private lateinit var currentPath: JFXTextField
    @FXML
    private lateinit var searchKey: JFXTextField
    @FXML
    private lateinit var fileInfoController: FileInfoView

    private lateinit var snackbar: JFXSnackbar

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
        fileList.items = ObservableListWrapper<CFile>(ArrayList<CFile>())
        fileList.setCellFactory { _ ->
            object : JFXListCell<CFile>() {

                init {
                    setOnDragEntered {
                        fileList.selectionModel.select(item)
                        it.consume()
                    }

                    setOnDragDropped {
                        if (it.dragboard.hasFiles()) {
                            fileList.selectionModel.selectedItem.addFile(it.dragboard.files)
                            it.consume()
                        }
                    }

                    setOnContextMenuRequested { _ ->
                        contextMenu = ContextMenu().apply {
                            items.addAll(
                                MenuItem(bundle!!.getString("General.rename")).apply {
                                    id = "rename"
                                    setOnAction {
                                        TextInputDialog().apply {
                                            title = bundle.getString("General.rename")
                                            showAndWait()
                                            if (result.isNullOrBlank().not()) {
                                                item.rename(result)
                                                updateItem(item, false)
                                            }
                                        }
                                    }
                                }
                            )
                        }
                    }
                }

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
        fileList.selectionModel.selectedItemProperty().addListener { _, _, selected ->
            selected?.also {
                fileInfoController.loadCFile(selected)
            }
        }
        fileList.setOnMouseClicked {
            if (it.clickCount == 2 && it.button == MouseButton.PRIMARY) {
                fileList.selectionModel.selectedItem?.also { selected ->
                    selected.executable().launch(object : CExecutable.Callback {
                        override fun onFailed() {
                            bundle?.also { bundle ->
                                showInfo(bundle.getString("MainView.OpenFileFailed"))
                            }
                        }
                    })
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
                carpo.addFile(event.dragboard.files)
                reloadUI()
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
        updateFileList(carpo.byKeyword(searchKey.text))
    }

    private fun reloadUI() {
        currentPath.text = carpo.workspace().rootJFile().absolutePath
        updateFileList(carpo.all())
    }

    private fun updateFileList(maps: Map<String, CFile>) {
        with(fileList.items) {
            maps.values.let { collections ->
                clear()
                addAll(collections)
            }
        }
    }

    @FXML
    private fun searchKeyFieldKeyPressed(keyEvent: KeyEvent) {
        if (keyEvent.code == KeyCode.ENTER) {
            searchByKey()
        }
    }

    private fun showInfo(message: String) {
        if (!::snackbar.isInitialized) {
            snackbar = JFXSnackbar(rootPane)
        }
        snackbar.enqueue(JFXSnackbar.SnackbarEvent(message))
    }
}