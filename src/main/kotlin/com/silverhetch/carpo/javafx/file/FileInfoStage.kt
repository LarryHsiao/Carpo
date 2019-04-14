package com.silverhetch.carpo.javafx.file

import com.silverhetch.carpo.file.CFile
import com.silverhetch.carpo.javafx.Stylesheets
import com.silverhetch.carpo.javafx.utility.jfoenix.JFXDecoratorParent
import com.silverhetch.clotho.Source
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import java.util.*

/**
 * Factory that build and show FileList stage with given [CFile]
 */
class FileInfoStage(private val resources: ResourceBundle, private val file: CFile, private val stage: Stage = Stage()) : Source<Unit> {
    override fun fetch() {
        stage.scene = Scene(
            FXMLLoader(javaClass.getResource("/FileList.fxml")).let { loader ->
                loader.resources = resources
                val view = loader.load<Parent>()
                loader.getController<FileListView>().loadList(file.subFiles().all())
                JFXDecoratorParent(stage, view).fetch()

            }, 640.0, 480.0
        ).also { it.stylesheets.addAll(Stylesheets().fetch()) }
        stage.show()
    }
}