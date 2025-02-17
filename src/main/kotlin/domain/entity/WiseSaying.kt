package com.ll.domain.entity

import com.ll.global.util.JsonUtil.jsonStrToMap

class WiseSaying(
    var id: Int = 0,
    var content: String,
    var author: String,
) {
    constructor(content: String, author: String) : this(0, content, author)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WiseSaying

        return id == other.id
    }

    companion object {
        fun fromJsonStr(jsonStr: String): WiseSaying {
            val map = jsonStrToMap(jsonStr)

            return WiseSaying(
                id =  map["id"] as Int,
                content = map["content"] as String,
                author = map["author"] as String
            )
        }
    }

    fun isNew(): Boolean {
        return id == 0
    }

    fun updateWiseSaying(content: String, author: String) {
        this.content = content
        this.author = author
    }

    override fun hashCode(): Int {
        return id
    }

    val jsonStr: String
        get() {
            return """
                {
                    "id": $id,
                    "content": "$content",
                    "author": "$author"
                }
            """.trimIndent()
        }
}