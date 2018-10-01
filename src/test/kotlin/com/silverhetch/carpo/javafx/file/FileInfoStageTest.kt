package com.silverhetch.carpo.javafx.file

import com.silverhetch.carpo.file.CFile
import com.silverhetch.carpo.file.SubCFile
import com.silverhetch.carpo.file.phantom.PhantomCFile
import com.silverhetch.carpo.workspace.CarpoWorkspace
import javafx.application.Platform
import javafx.stage.Stage
import org.junit.Assert
import org.junit.Test
import org.testfx.framework.junit.ApplicationTest
import java.io.File
import java.nio.file.Files
import java.util.*

class FileInfoStageTest : ApplicationTest() {
    private lateinit var stage: Stage

    override fun start(stage: Stage?) {
        super.start(stage)
        this.stage = stage!!
        stage.show()
    }

    @Test
    fun simple() {
        Platform.runLater {
            FileInfoStage(
                ResourceBundle.getBundle("i18n/default"),
                SubCFile(
                    CarpoWorkspace(
                        Files.createTempDirectory("").toFile()
                    ),
                    Files.createTempDirectory("").toFile().also { dir ->
                        File(dir, "1").createNewFile()
                        File(dir, "2").createNewFile()
                    },
                    PhantomCFile()
                ),
                stage
            ).fetch()
        }
        Thread.sleep(1000)

        Assert.assertEquals(
            2,
            from(lookup("#fileList")).queryListView<CFile>().items.size
        )
    }
}