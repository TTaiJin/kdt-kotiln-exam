package com.ll.domain.repository

import com.ll.domain.entity.WiseSaying

class WiseSayingMemoryRepository : WiseSayingRepository {

    private val wiseSayings: MutableList<WiseSaying> = mutableListOf()
    private var lastId: Int = 0


    override fun save(wiseSaying: WiseSaying) : WiseSaying {
        if(wiseSaying.isNew()) {
            wiseSaying.id = ++lastId
            wiseSayings.add(wiseSaying)
            return wiseSaying
        }
        return wiseSaying
    }

    override fun findAll() : List<WiseSaying> {
        return wiseSayings.toList()
    }

    override fun findById(id: Int) : WiseSaying? {
        return wiseSayings.firstOrNull {it.id == id}
    }

    override fun delete(id: Int): Boolean {
        return wiseSayings.remove(findById(id))
    }

    override fun clear() {
        lastId = 0
        wiseSayings.clear()
    }

    override fun build() {}

    override fun findByAuthorLike(authorLike: String): List<WiseSaying> {
        val pureKeyword = authorLike.replace("%", "")

        val wiseSayings = findAll()

        if (pureKeyword.isBlank()) return wiseSayings

        return if (authorLike.startsWith("%") && authorLike.endsWith("%")) {
            wiseSayings.filter { it.author.contains(pureKeyword) }
        } else if (authorLike.startsWith("%")) {
            wiseSayings.filter { it.author.endsWith(pureKeyword) }
        } else if (authorLike.endsWith("%")) {
            wiseSayings.filter { it.author.startsWith(pureKeyword) }
        } else {
            wiseSayings.filter { it.author == pureKeyword }
        }
    }

    override fun findByAuthorContent(contentLike: String): List<WiseSaying> {
        val pureKeyword = contentLike.replace("%", "")

        val wiseSayings = findAll()

        if (pureKeyword.isBlank()) return wiseSayings

        return if (contentLike.startsWith("%") && contentLike.endsWith("%")) {
            wiseSayings.filter { it.content.contains(pureKeyword) }
        } else if (contentLike.startsWith("%")) {
            wiseSayings.filter { it.content.endsWith(pureKeyword) }
        } else if (contentLike.endsWith("%")) {
            wiseSayings.filter { it.content.startsWith(pureKeyword) }
        } else {
            wiseSayings.filter { it.content == pureKeyword }
        }
    }
}