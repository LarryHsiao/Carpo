package com.silverhetch.carpo.edge

import org.junit.Assert
import org.junit.Test
import java.net.URI

class URITesting {
    @Test
    fun schema() {
        Assert.assertEquals(
            "carpo://silverhetch.com/tag",
            URI("carpo", "silverhetch.com", "/tag", null).toASCIIString()
        )
    }
}