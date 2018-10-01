package com.silverhetch.carpo.javafx.file

import com.jfoenix.controls.JFXListView
import com.silverhetch.carpo.file.CFile
import com.silverhetch.carpo.workspace.DefaultWorkspaceFile
import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import org.junit.Assert
import org.junit.Test
import org.testfx.framework.junit.ApplicationTest
import java.io.File
import java.util.*

class FileListViewTest : ApplicationTest() {
    override fun start(stage: Stage) {
        super.start(stage)
        SvgImageLoaderFactory.install()
        DefaultWorkspaceFile().fetch().let { it ->
            if (it.exists()) {
                it.deleteRecursively()
            }
            it.mkdir()
            File(it, UUID.randomUUID().toString().substring(0, 5)).createNewFile()
            File(it, UUID.randomUUID().toString().substring(0, 5)).createNewFile()
        }
        val parent = FXMLLoader.load<Parent>(
            javaClass.getResource("/Main.fxml"),
            ResourceBundle.getBundle("i18n/default")
        )
        stage.scene = Scene(parent)
        stage.show()
    }

    @Test
    fun contextMenuCount() {
        clickOn(from(lookup("#fileList")).lookup(".list-cell").nth(0).query<JFXListView<String>>())
        rightClickOn(from(lookup("#fileList")).lookup(".list-cell").nth(0).query<JFXListView<String>>())
        Thread.sleep(1000)

        Assert.assertEquals(
            1,
            from(lookup("#popup")).queryListView<CFile>().items.size
        )
    }

    @Test
    fun deletePopup() {
        clickOn(from(lookup("#fileList")).lookup(".list-cell").nth(0).query<JFXListView<String>>())
        rightClickOn(from(lookup("#fileList")).lookup(".list-cell").nth(0).query<JFXListView<String>>())
        Thread.sleep(1000)
        clickOn(from(lookup("#popup").nth(0)).queryListView<CFile>())
        Thread.sleep(1000)

        Assert.assertTrue(
            from(lookup("#deleteDialog")).query<Parent>().isVisible
        )
    }
}