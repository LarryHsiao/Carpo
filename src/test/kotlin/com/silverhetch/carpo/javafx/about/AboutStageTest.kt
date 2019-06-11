package com.silverhetch.carpo.javafx.about

import com.silverhetch.carpo.file.CFile
import javafx.application.Platform
import javafx.scene.control.TextArea
import javafx.stage.Stage
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import org.testfx.framework.junit.ApplicationTest
import java.util.*

class AboutStageTest : ApplicationTest() {
    private lateinit var stage: Stage
    override fun start(stage: Stage?) {
        super.start(stage)
        this.stage = stage!!
        stage.show()
    }

    @Test
    fun hasContent() {
        Platform.runLater {
            AboutStage(
                ResourceBundle.getBundle("i18n/default"),
                stage
            ).value()

            Assert.assertTrue(
                from(lookup("#aboutInfo")).query<TextArea>().text.isNotBlank()
            )
        }
    }
}