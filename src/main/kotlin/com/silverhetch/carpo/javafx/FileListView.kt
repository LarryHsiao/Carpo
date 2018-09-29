package com.silverhetch.carpo.javafx

import com.jfoenix.controls.JFXListCell
import com.jfoenix.controls.JFXListView
import com.jfoenix.controls.JFXPopup
import com.jfoenix.controls.JFXSnackbar
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
import javafx.scene.control.MultipleSelectionModel
import javafx.scene.control.SelectionMode
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseButton
import java.net.URL
import java.util.*

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
        fileList.setOnContextMenuRequested {event->
            if (fileList.selectionModel.selectedItems.size == 0) {
                return@setOnContextMenuRequested
            }
            JFXPopup().let { popup ->
                popup.popupContent = JFXListView<String>().also { listView ->
                    listView.items.addAll(bundle.getString("General.delete"))
                    listView.selectionModel.selectedIndexProperty().addListener { _, _, index ->
                        when (index) {
                            0 -> {
                                fileList.selectionModel.selectedItems.forEach {
                                    it.remove()
                                }
                                fileList.items.removeAll(fileList.selectionModel.selectedItems)
                            }
                        }
                        popup.hide()
                    }
                }
                popup.show(fileList, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, event.x, event.y)
            }
        }
        fileList.setCellFactory { _ ->
            object : JFXListCell<CFile>() {
                init {
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