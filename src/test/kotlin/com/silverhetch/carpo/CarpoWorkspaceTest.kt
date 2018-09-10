package com.silverhetch.carpo

import com.silverhetch.carpo.workspace.CarpoWorkspace
import org.junit.Assert.*
import org.junit.Test
import java.io.File
import java.nio.file.NotDirectoryException
import java.sql.SQLException

class CarpoWorkspaceTest {
    @Test
    fun invalidedRootFile_files() {
        try {
            CarpoWorkspace(
                File.createTempFile("prefix", "")
            ).files()
            fail()
        } catch (e: NotDirectoryException) {
            assertTrue(true)
        }
    }

    @Test
    fun invalidedRootFile_sql() {
        try {
            CarpoWorkspace(
                File.createTempFile("prefix", "")
            ).sqlConn()
            fail()
        } catch (e: RuntimeException) {
            assertEquals(SQLException::class.java, e.cause!!.javaClass)
        }
    }
}