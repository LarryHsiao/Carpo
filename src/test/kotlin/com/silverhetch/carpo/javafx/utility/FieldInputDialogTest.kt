package com.silverhetch.carpo.javafx.utility

import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import org.junit.Assert
import org.junit.Test
import org.testfx.framework.junit.ApplicationTest
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit
import org.testfx.util.WaitForAsyncUtils



class FieldInputDialogTest : ApplicationTest() {
    private lateinit var stage: Stage
    override fun start(stage: Stage?) {
        super.start(stage)
        this.stage = stage!!
        stage.scene = Scene(StackPane())
        stage.show()
    }

    @Test
    fun simple() {
        var result=""
        Platform.runLater {
            result = FieldInputDialog(
                stage,
                ResourceBundle.getBundle("i18n/default"),
                "1234"
            ).fetch()
        }
        WaitForAsyncUtils.waitFor(10, TimeUnit.SECONDS) {
            from(lookup(("#FieldInputDialogTextField"))).tryQuery<TextField>().isPresent
        }
        clickOn(lookup("#FieldInputDialogTextField").query<TextField>())
        write("NewTitle").push(KeyCode.ENTER)
        Thread.sleep(1000)
        Assert.assertEquals("NewTitle", result)
    }
}