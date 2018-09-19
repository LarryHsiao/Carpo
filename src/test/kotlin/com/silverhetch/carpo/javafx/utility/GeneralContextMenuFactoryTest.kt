package com.silverhetch.carpo.javafx.utility

import org.junit.Assert.fail
import org.junit.Test
import org.testfx.framework.junit.ApplicationTest
import java.util.*

class GeneralContextMenuFactoryTest : ApplicationTest() {

    @Test
    fun checkItemExist() {
        val checklist = arrayOf(
            "rename",
            "delete"
        )
        GeneralContextMenuFactory(object : ContextMenuFactory.Events {
            override fun onItemClicked(id: String) {
                fail()
            }
        }, ResourceBundle.getBundle("ui/i18n/default")).fetch().let { contextMenu ->
            checklist.forEach checkedItem@{ checkedId ->
                contextMenu.items.forEach menuItem@{
                    if (it.id == checkedId) {
                        return@checkedItem
                    }
                }
                fail("missing menu item id: $checkedId")
            }
        }
    }
}