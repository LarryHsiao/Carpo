package com.silverhetch.carpo.javafx

import com.silverhetch.carpo.file.phantom.PhantomCFile
import com.silverhetch.carpo.tag.Tag
import com.silverhetch.carpo.workspace.DefaultWorkspaceFile
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import org.testfx.assertions.api.Assertions.assertThat
import org.testfx.framework.junit.ApplicationTest
import java.io.File
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
            val parent = it.load<Parent>(
                javaClass.getResource("/ui/FileList.fxml").openStream()
            )
            fileListView = it.getController<FileListView>()
            stage.scene = Scene(parent)
            stage.show()
        }
    }

    @Test
    fun appendFile() {
        fileListView.appendCFile(PhantomCFile())

        assertEquals(
            1,
            from(lookup("#fileList")).queryListView<Tag>().items.size
        )

    }
}