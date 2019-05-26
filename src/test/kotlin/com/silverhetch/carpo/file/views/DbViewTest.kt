package com.silverhetch.carpo.file.views

import com.silverhetch.carpo.database.connection.CarpoDbConn
import com.silverhetch.clotho.database.sqlite.InMemoryConn
import org.junit.Assert.*
import org.junit.Test

class DbViewTest{


    @Test
    fun remove() {
        DbViews(
            CarpoDbConn(
                InMemoryConn()
            ).value()
        ).let {
            it.newView("").remove()
            assertEquals(
                0,
                it.all().size
            )
        }
    }
}