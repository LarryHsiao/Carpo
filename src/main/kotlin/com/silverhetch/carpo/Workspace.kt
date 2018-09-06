package com.silverhetch.carpo

import java.io.File
import java.sql.Connection

interface Workspace {
    fun rootJFile(): File
    fun files(): Array<File>
    fun sqlConn(): Connection
}