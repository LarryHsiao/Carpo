package com.silverhetch.carpo.javafx.file

import com.jfoenix.controls.*
import com.jfoenix.controls.JFXPopup.PopupHPosition.LEFT
import com.jfoenix.controls.JFXPopup.PopupVPosition.TOP
import com.silverhetch.carpo.file.CExecutable
import com.silverhetch.carpo.file.CFile
import com.silverhetch.carpo.file.comparetor.FileNameComparator
import com.silverhetch.carpo.javafx.utility.draging.JdkFileDraging
import com.silverhetch.carpo.javafx.utility.draging.MultiDraging
import com.silverhetch.carpo.javafx.utility.draging.TagDraging
import com.silverhetch.clotho.utility.comparator.StringComparator
import com.sun.javafx.collections.ObservableListWrapper
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import javafx.scene.control.MultipleSelectionModel
import javafx.scene.control.SelectionMode
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.ClipboardContent
import javafx.scene.input.DataFormat
import javafx.scene.input.MouseButton
import javafx.scene.input.TransferMode
import javafx.stage.Modality.APPLICATION_MODAL
import javafx.stage.Stage
import java.io.File
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

/**
 * Represent list with given [CFile]
 */
class FileListView : Initializable {
    @FXML
    private lateinit var fileList: JFXListView<CFile>
    @FXML
    private lateinit var infoBar: JFXSnackbar

    override fun initialize(location: URL?, bundle: ResourceBundle) {
        fileList.items = ObservableListWrapper<CFile>(ArrayList<CFile>())
        fileList.selectionModel.selectionMode = SelectionMode.MULTIPLE
        fileList.setOnContextMenuRequested { event ->
            if (fileList.selectionModel.selectedItems.size == 0) {
                return@setOnContextMenuRequested
            }
            val popup = JFXPopup()
            popup.popupContent = JFXListView<String>().also { listView ->
                listView.items.addAll(bundle.getString("General.delete"))
                listView.selectionModel.selectedIndexProperty().addListener { _, _, index ->
                    when (index) {
                        0 -> {
                            val dialog = JFXAlert<Unit>(fileList.scene.window as Stage)
                            dialog.initModality(APPLICATION_MODAL)
                            dialog.isHideOnEscape = false
                            dialog.isOverlayClose = false
                            dialog.setContent(JFXDialogLayout().also { layout ->
                                layout.setHeading(Label(bundle.getString("General.delete")))
                                layout.setBody(Label(bundle.getString("General.deleteSelected")))
                                layout.setActions(
                                    JFXButton(bundle.getString("General.confirm")).also { button ->
                                        button.setOnAction {
                                            fileList.selectionModel.selectedItems.forEach { cFile ->
                                                cFile.remove()
                                            }
                                            fileList.items.removeAll(fileList.selectionModel.selectedItems)
                                            dialog.hideWithAnimation()
                                        }
                                    },
                                    JFXButton(bundle.getString("General.cancel")).also { button ->
                                        button.setOnAction { dialog.hideWithAnimation() }
                                    }
                                )
                            })
                            dialog.showAndWait()
                        }
                    }
                    popup.hide()
                }
            }
            popup.show(fileList, TOP, LEFT, event.x, event.y)
        }
        fileList.setCellFactory { _ ->
            object : JFXListCell<CFile>() {
                init {
                    setOnDragDetected { event ->
                        val dragboard = startDragAndDrop(TransferMode.LINK)
                        dragboard.setContent(ClipboardContent().also {
                            it[DataFormat.FILES] = ArrayList<File>().also { jdkFileList ->
                                fileList.selectionModel.selectedItems.forEach { file ->
                                    jdkFileList.add(file.jdkFile())
                                }
                            }
                        })
                        event.consume()
                    }
                    setOnDragEntered {
                        fileList.selectionModel.select(item)
                        it.consume()
                    }

                    MultiDraging(
                        JdkFileDraging {
                            fileList.selectionModel.selectedItem.addFile(it)
                            updateItem(item, false)
                        },
                        TagDraging {
                            item.tags().addTag(it)
                            fileList.selectionModel.clearSelection()
                            fileList.selectionModel.select(item)
                        }
                    ).let {
                        onDragOver = it
                        onDragDropped = it
                    }
                }

                override fun updateItem(item: CFile?, empty: Boolean) {
                    super.updateItem(item, empty)
                    if (empty) {
                        text = ""
                        graphic = null
                    } else {
                        if (item != null) {
                            text = item.title()
                            graphic = ImageView(Image(item.thumbnailUrl(), 36.0, 36.0, true, true))
                        }
                    }
                }
            }
        }
        fileList.setOnMouseClicked {
            if (it.clickCount == 2 && it.button == MouseButton.PRIMARY) {
                fileList.selectionModel.selectedItem?.also { selected ->
                    if (selected.jdkFile().isDirectory && selected.jdkFile().listFiles().size > 2 ) {
                        FileInfoStage(bundle, selected).fetch()
                    } else {
                        selected.executable().launch(object : CExecutable.Callback {
                            override fun onFailed() {
                                bundle.also { bundle ->
                                    if (::infoBar.isInitialized) {
                                        Platform.runLater {
                                            infoBar.enqueue(JFXSnackbar.SnackbarEvent(bundle.getString("MainView.OpenFileFailed")))
                                        }
                                    }
                                }
                            }
                        })
                    }
                }
            }
        }
    }

    /**
     * Replace list content with given [CFile].
     */
    fun loadList(maps: Map<String, CFile>) {
        with(fileList.items) {
            maps.values.let { collections ->
                clear()
                addAll(
                    collections.sortedWith(
                        FileNameComparator(
                            StringComparator()
                        )
                    )
                )
            }
        }
    }

    /**
     * Convenient function to pass General purpose snackbar instance.
     */
    fun setup(infoBar: JFXSnackbar) {
        this.infoBar = infoBar
    }

    /**
     * Append [CFile] to this List
     */
    fun appendCFile(cFile: CFile) {
        fileList.items.add(cFile)
        fileList.selectionModel.select(cFile)
        fileList.scrollTo(cFile)
    }

    /**
     * The selection model of ListView
     */
    fun selectionModel(): MultipleSelectionModel<CFile> {
        return fileList.selectionModel
    }
}