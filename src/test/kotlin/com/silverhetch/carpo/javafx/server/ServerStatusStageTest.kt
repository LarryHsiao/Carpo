package com.silverhetch.carpo.javafx.server

import com.silverhetch.carpo.CarpoImpl
import com.silverhetch.carpo.workspace.CarpoWorkspace
import javafx.application.Platform
import javafx.scene.Parent
import javafx.stage.Stage
import org.junit.Assert
import org.junit.Test
import org.testfx.framework.junit.ApplicationTest
import java.nio.file.Files
import java.util.*

class ServerStatusStageTest : ApplicationTest() {
    private lateinit var stage: Stage
    override fun start(stage: Stage?) {
        super.start(stage)
        this.stage = stage!!
        stage.show()
    }

    @Test
    fun simple() {
        Platform.runLater {
            ServerStatusStage(
                ResourceBundle.getBundle("i18n/default"),
                CarpoImpl(
                    CarpoWorkspace(
                        Files.createTempDirectory("prefix").toFile()
                    )
                ),
                stage
            ).fetch()


            Assert.assertTrue(
                from(lookup("#SeverStatusView")).query<Parent>().isVisible
            )
        }
    }
}