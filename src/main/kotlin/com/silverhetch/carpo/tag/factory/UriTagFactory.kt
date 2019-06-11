package com.silverhetch.carpo.tag.factory

import com.silverhetch.carpo.tag.Tag
import com.silverhetch.carpo.tag.Tags
import com.silverhetch.clotho.Source
import java.net.URI

/**
 * Factory class that handles the Uri <-> [Tag] mapping.
 */
class UriTagFactory {
    companion object {
        private const val URI_SCHEMA = "carpo"
        private const val URI_HOST = "silverhetch.com"
        private const val URI_PATH = "/tag/"
        private const val URI_PREFIX = "carpo://silverhetch.com/tag/"
    }

    /**
     * Uri of given [Tag]
     */
    class TagUri(private val obj: Tag) : Source<String> {
        override fun value(): String {
            return URI(URI_SCHEMA, URI_HOST, URI_PATH + obj.title(), null).toASCIIString()
        }
    }

    /**
     * [Tag] instance of given uri.
     */
    class UriTag(private val uri: String, private val tags: Tags) : Source<Tag> {
        override fun value(): Tag {
            TagName(uri).value().let { tagName ->
                return tags.byName(tagName)[tagName] ?: tags.addTag(tagName)
            }
        }
    }

    /**
     * Tag name in given uri.
     */
    class TagName(private val uri: String) : Source<String> {
        override fun value(): String {
            return URI.create(uri).path.replace("/tag/", "")
        }
    }

    /**
     * Determine if the given uri String represents a [Tag].
     */
    fun isValidUri(uri: String): Boolean {
        return uri.startsWith(URI_PREFIX)
    }
}