package com.silverhetch.carpo.tag.factory

import com.silverhetch.carpo.tag.Tag
import com.silverhetch.carpo.tag.Tags
import com.silverhetch.clotho.Source
import java.net.URI

class UriTagFactory {
    companion object {
        private const val URI_SCHEMA = "carpo"
        private const val URI_HOST = "silverhetch.com"
        private const val URI_PATH = "/tag/"
        private const val URI_PREFIX = "carpo://silverhetch.com/tag/"
    }

    class TagUri(private val obj: Tag) : Source<String> {
        override fun fetch(): String {
            return URI(URI_SCHEMA, URI_HOST, URI_PATH + obj.title(), null).toASCIIString()
        }
    }

    class UriTag(private val uri: String, private val tags: Tags) : Source<Tag> {
        override fun fetch(): Tag {
            TagName(uri).fetch().let { tagName ->
                return tags.byName(tagName)[tagName] ?: tags.addTag(tagName)
            }
        }
    }

    class TagName(private val uri: String) : Source<String> {
        override fun fetch(): String {
            return URI.create(uri).path.replace("/tag/", "")
        }
    }

    fun isValidUri(uri: String): Boolean {
        return uri.startsWith(URI_PREFIX)
    }
}