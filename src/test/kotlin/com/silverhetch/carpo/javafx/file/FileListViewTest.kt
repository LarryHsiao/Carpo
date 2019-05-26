package com.silverhetch.carpo.javafx.file

import com.jfoenix.controls.JFXListView
import com.silverhetch.carpo.config.CarpoConfigSource
import com.silverhetch.carpo.file.CFile
import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.input.MouseButton
import javafx.stage.Stage
import org.junit.Assert
import org.junit.Test
import org.testfx.framework.junit.ApplicationTest
import org.testfx.robot.Motion
import java.io.File
import java.util.*

class FileListViewTest : ApplicationTest() {
    override fun start(stage: Stage) {
        super.start(stage)
        SvgImageLoaderFactory.install()
        CarpoConfigSource().value().apply {
            clear()
            val root = File(workspacePath()).apply {
                deleteRecursively()
                mkdirs()
            }
            File(root, UUID.randomUUID().toString().substring(0, 5)).createNewFile()
            File(root, UUID.randomUUID().toString().substring(0, 5)).createNewFile()
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
        clickOn(from(lookup("#fileList")).lookup(".list-cell").nth(0).query<JFXListView<String>>(), Motion.DEFAULT, MouseButton.SECONDARY)

        Assert.assertEquals(
            3,
            from(lookup("#popup")).queryListView<CFile>().items.size
        )
    }

    @Test
    fun deletePopup() {
        clickOn(from(lookup("#fileList")).lookup(".list-cell").nth(0).query<JFXListView<String>>())
        clickOn(from(lookup("#fileList")).lookup(".list-cell").nth(0).query<JFXListView<String>>(), Motion.DEFAULT, MouseButton.SECONDARY)
        Thread.sleep(500)
        clickOn(from(lookup("#popup").nth(0)).queryListView<CFile>())

        Assert.assertTrue(
            from(lookup("#deleteDialog")).query<Parent>().isVisible
        )
    }
}