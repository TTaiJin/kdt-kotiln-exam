package com.ll.domain.entity

import com.ll.global.util.JsonUtil.jsonStrToMap

class WiseSaying(
    var id: Int = 0,
    var content: String,
    var author: String,
) {
    constructor(content: String, author: String) : this(0, content, author)

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