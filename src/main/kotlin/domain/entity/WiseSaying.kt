package com.ll.domain.entity

class WiseSaying(
    var content: String,
    var author: String,
) {
    var id: Int = 0

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