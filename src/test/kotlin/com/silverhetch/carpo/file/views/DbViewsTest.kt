package com.silverhetch.carpo.file.views

import com.silverhetch.carpo.database.connection.CarpoDbConn
import com.silverhetch.clotho.database.sqlite.InMemoryConn
import org.junit.Assert.*
import org.junit.Test

class DbViewsTest{
    @Test
    fun newView() {
        DbViews(
            CarpoDbConn(
                InMemoryConn()
            ).value()
        ).let {
         it.newView("")
            assertEquals(
                1,
                it.all().size
            )
        }
    }
}