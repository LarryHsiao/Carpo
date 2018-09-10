package com.silverhetch.carpo.javafx

import com.jfoenix.controls.JFXListView
import com.jfoenix.controls.JFXTextField
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.stage.Stage
import org.junit.Test
import org.testfx.assertions.api.Assertions.assertThat
import org.testfx.framework.junit.ApplicationTest
import java.util.*

class MainViewTest : ApplicationTest() {
    private lateinit var parent: Parent

    override fun start(stage: Stage) {
        super.start(stage)
        parent = FXMLLoader.load<Parent>(
            javaClass.getResource("/ui/Main.fxml"),
            ResourceBundle.getBundle("ui/i18n/default")
        )
        stage.scene = Scene(parent)
        stage.show()
    }

    @Test
    fun newTagToFile() {
        clickOn(from(lookup("#fileList")).lookup(".list-cell").nth(0).query<JFXListView<String>>())
        clickOn(lookup("#tagName").query<JFXTextField>())
        write("AutoTag").push(KeyCode.ENTER)

        assertThat(
            from(lookup("#tagList")).queryListView<String>()
        ).hasListCell("AutoTag")
    }

    @Test
    fun existTagToFile() {
        val existTagName = UUID.randomUUID().toString()
        clickOn(from(lookup("#fileList")).lookup(".list-cell").nth(0).query<JFXListView<String>>())
        clickOn(lookup("#tagName").query<JFXTextField>())
        write(existTagName).push(KeyCode.ENTER)

        clickOn(from(lookup("#fileList")).lookup(".list-cell").nth(1).query<JFXListView<String>>())
        clickOn(lookup("#tagName").query<JFXTextField>())
        write(existTagName).push(KeyCode.ENTER)

        assertThat(
            from(lookup("#tagList")).queryListView<String>()
        ).hasListCell(existTagName)
    }
}