package com.silverhetch.carpo.javafx

import com.silverhetch.clotho.Source

class Stylesheets : Source<List<String>> {
    override fun fetch(): List<String> {
        return listOf(
            javaClass.getResource("/css/General.css").toExternalForm(),
            javaClass.getResource("/css/jfoenix-design.css").toExternalForm(),
            javaClass.getResource("/css/jfoenix-fonts.css").toExternalForm(),
            javaClass.getResource("/css/ToolBar.css").toExternalForm(),
            javaClass.getResource("/css/Decorator.css").toExternalForm(),
            javaClass.getResource("/css/ListView.css").toExternalForm(),
            javaClass.getResource("/css/Button.css").toExternalForm(),
            javaClass.getResource("/css/TextField.css").toExternalForm()
        )
    }
}