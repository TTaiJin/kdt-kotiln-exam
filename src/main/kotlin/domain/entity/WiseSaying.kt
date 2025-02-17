package com.ll.domain.entity

class WiseSaying(
    private var content: String,
    private var author: String,
    private var id: Int = 0
) {
    fun getContent(): String = content

    fun getAuthor(): String = author

    fun getId(): Int = id

    fun setId(id: Int) {
        this.id = id
    }

    fun isNew(): Boolean {
        return id == 0
    }


    fun updateWiseSaying(content: String, author: String) {
        this.content = content
        this.author = author
    }

}