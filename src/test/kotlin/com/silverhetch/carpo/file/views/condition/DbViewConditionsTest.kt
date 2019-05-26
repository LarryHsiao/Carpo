package com.silverhetch.carpo.file.views.condition

import com.silverhetch.carpo.database.connection.CarpoDbConn
import com.silverhetch.carpo.tag.PhantomTag
import com.silverhetch.clotho.database.sqlite.InMemoryConn
import org.junit.Assert.assertEquals
import org.junit.Test

class DbViewConditionsTest {
    @Test
    fun newCondition() {
        DbViewConditions(
            CarpoDbConn(
                InMemoryConn()
            ).value()
            , 1
        ).let {
            it.newCondition(PhantomTag(), Condition.Type.INCLUDED)
            assertEquals(1, it.all().size)
            assertEquals(-1, it.all()[0].tagId())
            assertEquals(1, it.all()[0].viewId())
        }
    }
}