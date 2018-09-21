package com.silverhetch.carpo.server

import com.google.gson.JsonArray
import com.silverhetch.carpo.tag.Tags
import org.takes.Take
import org.takes.facets.fork.FkMethods
import org.takes.facets.fork.FkRegex
import org.takes.facets.fork.FkWrap
import org.takes.facets.fork.TkFork
import org.takes.rs.RsText

class TkGetTags(private val tags: Tags) :
    FkWrap(
        FkRegex(
            "/tags",
            TkFork(
                FkMethods(
                    "GET",
                    Take { _ ->
                        RsText(
                            JsonArray().also { array ->
                                tags.all().forEach {
                                   array.add(it.value.title())
                                }
                            }.toString()
                        )
                    }
                )
            )
        )
    )