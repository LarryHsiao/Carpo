package com.silverhetch.carpo.javafx

import com.jfoenix.controls.JFXListCell
import com.jfoenix.controls.JFXListView
import com.jfoenix.controls.JFXSnackbar
import com.silverhetch.carpo.file.CExecutable
import com.silverhetch.carpo.file.CFile
import com.silverhetch.carpo.file.comparetor.FileNameComparator
import com.silverhetch.clotho.utility.comparator.StringComparator
import com.sun.javafx.collections.ObservableListWrapper
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.scene.control.MultipleSelectionModel
import javafx.scene.control.TextInputDialog
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseButton
import java.net.URL
import java.util.*

class FileListView : Initializable {
    @FXML
    private lateinit var fileList: JFXListView<CFile>
    @FXML
    private lateinit var infoBar: JFXSnackbar

    override fun initialize(location: URL?, bundle: ResourceBundle?) {
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
                            updateItem(item, false)
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
                                            this.headerText = bundle.getString("General.rename.hint")
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
                    selected.executable().launch(object : CExecutable.Callback {
                        override fun onFailed() {
                            bundle?.also { bundle ->
                                if (::infoBar.isInitialized) {
                                    infoBar.enqueue(JFXSnackbar.SnackbarEvent(bundle.getString("MainView.OpenFileFailed")))
                                }
                            }
                        }
                    })
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
    }

    /**
     * The selection model of ListView
     */
    fun selectionModel(): MultipleSelectionModel<CFile> {
        return fileList.selectionModel
    }
}