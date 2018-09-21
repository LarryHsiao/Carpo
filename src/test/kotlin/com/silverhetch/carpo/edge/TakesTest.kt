package com.silverhetch.carpo.edge

import com.silverhetch.clotho.connection.Get
import com.silverhetch.clotho.connection.TargetImpl
import org.junit.Assert
import org.junit.Test
import org.takes.facets.fork.FkRegex
import org.takes.facets.fork.TkFork
import org.takes.http.FtBasic
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit.SECONDS

class TakesTest {
    @Test
    fun simple() {
        var exit = false
        Thread {
            FtBasic(
                TkFork(FkRegex("/", "Hello world!")), 8080
            ).start { exit }
        }.start()

        CountDownLatch(1).await(1, SECONDS)

        Assert.assertEquals(
            "Hello world!",
            String(
                Get(
                    TargetImpl(
                        "http://localhost:8080/"
                    )
                ).request().bodyBytes
            )
        )
        exit = true

    }
}