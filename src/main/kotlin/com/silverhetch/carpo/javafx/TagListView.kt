package com.silverhetch.carpo.javafx

import com.jfoenix.controls.*
import com.jfoenix.controls.JFXPopup.PopupHPosition
import com.jfoenix.controls.JFXPopup.PopupVPosition
import com.silverhetch.carpo.javafx.tag.overview.TagOverviewStage
import com.silverhetch.carpo.tag.Tag
import com.silverhetch.carpo.tag.TagNameComparator
import com.silverhetch.carpo.tag.factory.UriTagFactory
import com.silverhetch.clotho.utility.comparator.StringComparator
import com.sun.javafx.collections.ObservableListWrapper
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import javafx.scene.control.SelectionMode
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.ClipboardContent
import javafx.scene.input.DataFormat
import javafx.scene.input.MouseButton.PRIMARY
import javafx.scene.input.TransferMode
import javafx.stage.Stage
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList


/**
 * Represent list with given [Tag].
 */
class TagListView : Initializable {
    @FXML
    private lateinit var tagList: JFXListView<Tag>

    override fun initialize(location: URL?, resources: ResourceBundle) {
        tagList.items = ObservableListWrapper<Tag>(ArrayList<Tag>())
        tagList.selectionModel.selectionMode = SelectionMode.MULTIPLE
        tagList.setOnContextMenuRequested { event ->
            if (tagList.selectionModel.selectedItems.size == 0) {
                return@setOnContextMenuRequested
            }
            JFXPopup().let { popup ->
                popup.popupContent = JFXListView<String>().also { listView ->
                    listView.items.addAll(
                        resources.getString("General.delete")
                    )
                    listView.selectionModel.selectedIndexProperty().addListener { _, _, index ->
                        when (index) {
                            0 -> {
                                JFXAlert<Unit>(tagList.scene.window as Stage).also { dialog ->
                                    dialog.isHideOnEscape = false
                                    dialog.isOverlayClose = false
                                    dialog.setContent(JFXDialogLayout().also { layout ->
                                        layout.setHeading(Label(resources.getString("General.delete")))
                                        layout.setBody(Label(resources.getString("General.deleteSelected")))
                                        layout.setActions(
                                            JFXButton(resources.getString("General.confirm")).also { button ->
                                                button.setOnAction {
                                                    tagList.selectionModel.selectedItems.forEach { tag ->
                                                        tag.remove()
                                                    }
                                                    tagList.items.removeAll(tagList.selectionModel.selectedItems)
                                                    dialog.hideWithAnimation()
                                                }
                                            },
                                            JFXButton(resources.getString("General.cancel")).also { button ->
                                                button.setOnAction { dialog.hideWithAnimation() }
                                            }
                                        )
                                    })
                                }.showAndWait()
                            }
                        }
                        popup.hide()
                    }
                }
                popup.show(tagList, PopupVPosition.TOP, PopupHPosition.LEFT, event.x, event.y)
            }
        }
        tagList.setCellFactory {
            object : JFXListCell<Tag>() {
                init {
                    setOnDragDetected { dropEvent ->
                        val dragboard = startDragAndDrop(TransferMode.MOVE, TransferMode.LINK)
                        dragboard.setContent(ClipboardContent().also { content ->
                            content[DataFormat.URL] = UriTagFactory.TagUri(item).fetch()
                            dragboard.dragView = Image(
                                javaClass.getResource("/icon/tag.svg").toURI().toString(),
                                32.0,
                                32.0,
                                true,
                                true
                            )
                        })
                        dropEvent.consume()
                    }
                }

                override fun updateItem(item: Tag?, empty: Boolean) {
                    super.updateItem(item, empty)
                    if (empty) {
                        text = ""
                        graphic = null
                    } else {
                        text = item?.title() ?: ""
                        graphic = ImageView(
                            Image(
                                javaClass.getResource("/icon/tag.svg").toURI().toString(),
                                32.0,
                                32.0,
                                true,
                                true)
                        )
                    }
                }
            }
        }
        tagList.setOnMouseClicked { mouseEvent ->
            if (mouseEvent.button == PRIMARY && mouseEvent.clickCount == 2) {
                tagList.selectionModel.selectedItem?.also { selected ->
                    TagOverviewStage(resources, selected).fetch()
                }
            }
        }
    }

    /**
     * Load list with given tags.
     */
    fun loadTags(tags: Map<String, Tag>) {
        with(tagList.items) {
            clear()
            addAll(tags.values.sortedWith(
                TagNameComparator(
                    StringComparator()
                )
            ))
        }
    }
}