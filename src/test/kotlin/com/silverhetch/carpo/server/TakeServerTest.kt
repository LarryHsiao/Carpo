package com.silverhetch.carpo.server

import com.silverhetch.carpo.CarpoImpl
import com.silverhetch.carpo.workspace.CarpoWorkspace
import com.silverhetch.carpo.workspace.DefaultWorkspaceFile
import com.silverhetch.clotho.connection.Get
import com.silverhetch.clotho.connection.TargetImpl
import org.junit.Assert.*
import org.junit.Test

class TakeServerTest {
    @Test
    fun simple() {
        TakeServer(CarpoImpl(
            CarpoWorkspace(
                DefaultWorkspaceFile().fetch()
            )
        )).let { server ->
            server.start()
            Thread.sleep(500)

            assertEquals(
                200,
                Get(TargetImpl("http://localhost:8080/tags")).request().code
            )
            server.shutdown()
            Thread.sleep(1000)
        }
    }


    @Test
    fun shutdown() {
        TakeServer(CarpoImpl(
            CarpoWorkspace(
                DefaultWorkspaceFile().fetch()
            )
        )).let {
            it.start()
            Thread.sleep(500)
            it.shutdown()
            Thread.sleep(1000)
        }

        try {
            Get(TargetImpl("http://localhost:8080")).request()
            fail()
        } catch (e: Exception) {
            assertTrue(true)
        }
    }
}