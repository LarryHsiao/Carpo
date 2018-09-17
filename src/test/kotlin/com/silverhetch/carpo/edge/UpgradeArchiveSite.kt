package com.silverhetch.carpo.edge

import com.silverhetch.clotho.connection.Get
import com.silverhetch.clotho.connection.TargetImpl
import org.junit.Assert
import org.junit.Test
import sun.security.ssl.SSLSocketFactoryImpl

class UpgradeArchiveSite {
    @Test
    fun checkAvailable() {
        Assert.assertEquals(
            200,
            Get(
                TargetImpl(
                    "https://silverhetch.com:12000/",
                    SSLSocketFactoryImpl()
                )
            ).request().code
        )
    }
}