package com.silverhetch.carpo.tag

import com.silverhetch.clotho.utility.comparator.StringComparator
import org.junit.Assert.assertEquals
import org.junit.Test

class TagNameComparatorTest {
    @Test
    fun simpleGrater() {
        assertEquals(
            1,
            TagNameComparator(
                StringComparator()
            ).compare(
                PhantomTag("1"),
                PhantomTag("2")
            )
        )
    }

    @Test
    fun simpleLesser() {
        assertEquals(
            -1,
            TagNameComparator(
                StringComparator()
            ).compare(
                PhantomTag("2"),
                PhantomTag("1")
            )
        )
    }


    @Test
    fun simpleEqual() {
        assertEquals(
            0,
            TagNameComparator(
                StringComparator()
            ).compare(
                PhantomTag("2"),
                PhantomTag("2")
            )
        )
    }

}