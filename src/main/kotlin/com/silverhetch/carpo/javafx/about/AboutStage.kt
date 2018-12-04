package com.silverhetch.carpo.javafx.about

import com.silverhetch.carpo.javafx.Stylesheets
import com.silverhetch.carpo.javafx.file.FileListView
import com.silverhetch.carpo.javafx.utility.jfoenix.JFXDecoratorParent
import com.silverhetch.clotho.Source
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import java.util.*


class AboutStage(private val resources: ResourceBundle, private val stage: Stage = Stage()) : Source<Unit> {
    override fun fetch() {
        stage.scene = Scene(
            FXMLLoader(javaClass.getResource("/About.fxml")).let { loader ->
                loader.resources = resources
                val view = loader.load<Parent>()
                view.id = "aboutPage"
                JFXDecoratorParent(stage, view).fetch()
            }, 640.0, 480.0
        ).also { it.stylesheets.addAll(Stylesheets().fetch()) }
        stage.show()
    }
}