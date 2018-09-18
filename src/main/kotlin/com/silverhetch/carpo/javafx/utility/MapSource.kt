package com.silverhetch.carpo.javafx.utility

import com.silverhetch.clotho.Source
import javafx.scene.control.ContextMenu
import java.util.*

class GeneralContextMenuFactory(private val events: ContextMenuFactory.Events, private val bundle: ResourceBundle) : Source<ContextMenu> {
    override fun fetch(): ContextMenu {
        return ContextMenuFactory(mapOf(
            Pair("rename", bundle.getString("General.rename")),
            Pair("delete", bundle.getString("General.delete"))
        ), events).fetch()
    }
}