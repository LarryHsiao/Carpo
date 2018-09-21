package com.silverhetch.carpo.javafx

import com.silverhetch.carpo.file.phantom.PhantomCFile
import com.silverhetch.carpo.tag.Tag
import com.silverhetch.carpo.workspace.DefaultWorkspaceFile
import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import org.junit.Assert.assertEquals
import org.junit.Test
import org.testfx.framework.junit.ApplicationTest
import java.util.*

class FileListViewTest : ApplicationTest() {
    private lateinit var fileListView: FileListView

    override fun start(stage: Stage) {
        super.start(stage)
        DefaultWorkspaceFile().fetch().let {
            if (it.exists()) {
                it.deleteRecursively()
            }
        }
        FXMLLoader().let {
            it.resources = ResourceBundle.getBundle("ui/i18n/default")
            it.location = javaClass.getResource("/ui/FileList.fxml")
            stage.scene = Scene(it.load())
            fileListView = it.getController<FileListView>()
            stage.show()
        }
    }

    @Test
    fun appendFile() {
        Platform.runLater {
            fileListView.appendCFile(PhantomCFile())
        }

        assertEquals(
            1,
            from(lookup("#fileList")).queryListView<Tag>().items.size
        )
    }
}