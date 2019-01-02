package com.silverhetch.carpo.javafx.about

import com.jfoenix.controls.JFXTextArea
import com.silverhetch.BuildConfig
//import com.silverhetch.carpo.Consts
import javafx.fxml.FXML
import javafx.fxml.Initializable
import java.net.URL
import java.util.*


class AboutView : Initializable {
    @FXML private lateinit var aboutInfo: JFXTextArea

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        aboutInfo.text = BuildConfig.NAME
    }
}