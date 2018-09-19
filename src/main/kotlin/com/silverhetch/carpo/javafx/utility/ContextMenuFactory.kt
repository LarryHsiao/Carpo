package com.silverhetch.carpo.javafx.utility

import com.silverhetch.clotho.Source
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem

/**
 * @param map K: the id of menu item, V: the display String
 */
class ContextMenuFactory(private val map: Map<String, String>, private val events: Events) : Source<ContextMenu> {
    interface Events {
        fun onItemClicked(id: String)
    }

    override fun fetch(): ContextMenu {
        return ContextMenu().apply {
            map.forEach { id, title ->
                items.add(MenuItem(title).also { menuItem ->
                    menuItem.id = id
                    menuItem.setOnAction {
                        events.onItemClicked(menuItem.id)
                        it.consume()
                    }
                })
            }
        }
    }
}