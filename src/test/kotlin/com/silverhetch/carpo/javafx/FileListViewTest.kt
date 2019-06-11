package com.silverhetch.carpo.javafx

import com.silverhetch.carpo.config.CarpoConfigSource
import com.silverhetch.carpo.file.phantom.PhantomCFile
import com.silverhetch.carpo.javafx.file.FileListView
import com.silverhetch.carpo.tag.Tag
import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import org.junit.Assert.assertEquals
import org.junit.Test
import org.testfx.framework.junit.ApplicationTest
import java.io.File
import java.util.*

class FileListViewTest : ApplicationTest() {
    private lateinit var fileListView: FileListView

    override fun start(stage: Stage) {
        super.start(stage)
        CarpoConfigSource().value().apply {
            clear()
            val root = File(workspacePath()).apply {
                deleteRecursively()
                mkdirs()
            }
            File(root, UUID.randomUUID().toString().substring(0, 5)).createNewFile()
            File(root, UUID.randomUUID().toString().substring(0, 5)).createNewFile()
        }
        FXMLLoader().let {
            it.resources = ResourceBundle.getBundle("i18n/default")
            it.location = javaClass.getResource("/FileList.fxml")
            stage.scene = Scene(it.load())
            fileListView = it.getController<FileListView>()
            stage.show()
        }
    }

    @Test
    fun appendFile() {
        Platform.runLater {
            fileListView.appendCFile(PhantomCFile())
            assertEquals(
                1,
                from(lookup("#fileList")).queryListView<Tag>().items.size
            )
        }
    }
}