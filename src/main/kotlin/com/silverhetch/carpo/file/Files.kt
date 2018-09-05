package com.silverhetch.carpo.file

interface Files {
    fun all(): List<CFile>
    fun add(fileName:String)
}