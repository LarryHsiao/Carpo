package com.silverhetch.carpo.server

import com.google.gson.JsonArray
import com.silverhetch.carpo.file.CFile
import com.silverhetch.carpo.file.Files
import org.takes.Take
import org.takes.facets.fork.FkMethods
import org.takes.facets.fork.FkRegex
import org.takes.facets.fork.FkWrap
import org.takes.facets.fork.TkFork
import org.takes.rs.RsText

/**
 * APIs for [CFile]
 */
class TkFiles(private val files: Map<String, CFile>) :
    FkWrap(
        FkRegex(
            "/files",
            TkFork(
                FkMethods(
                    "GET",
                    Take { _ ->
                        RsText(
                            JsonArray().also { array ->
                                files.forEach {
                                    array.add(it.value.title())
                                }
                            }.toString()
                        )

                    }
                )
            )
        )
    )