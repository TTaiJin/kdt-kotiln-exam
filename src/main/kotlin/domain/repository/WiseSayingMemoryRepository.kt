package com.ll.domain.repository

import com.ll.domain.entity.WiseSaying
import com.ll.global.dto.Page

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
        return wiseSayings.reversed()
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
            findAll().filter { it.author.contains(pureKeyword) }
        } else if (authorLike.startsWith("%")) {
            findAll().filter { it.author.endsWith(pureKeyword) }
        } else if (authorLike.endsWith("%")) {
            findAll().filter { it.author.startsWith(pureKeyword) }
        } else {
            findAll().filter { it.author == pureKeyword }
        }
    }

    override fun findByAuthorContent(contentLike: String): List<WiseSaying> {
        val pureKeyword = contentLike.replace("%", "")

        val wiseSayings = findAll()

        if (pureKeyword.isBlank()) return wiseSayings

        return if (contentLike.startsWith("%") && contentLike.endsWith("%")) {
            findAll().filter { it.content.contains(pureKeyword) }
        } else if (contentLike.startsWith("%")) {
            findAll().filter { it.content.endsWith(pureKeyword) }
        } else if (contentLike.endsWith("%")) {
            findAll().filter { it.content.startsWith(pureKeyword) }
        } else {
            findAll().filter { it.content == pureKeyword }
        }
    }

    override fun findAllPaged(itemsPerPage: Int, pageNo: Int): Page<WiseSaying> {
        val content = findAll()
            .drop((pageNo - 1) * itemsPerPage)
            .take(itemsPerPage)

        return Page(wiseSayings.size, itemsPerPage, pageNo, "", "", content)
    }

    override fun findByKeywordPaged(
        keywordType: String,
        keyword: String,
        itemsPerPage: Int,
        pageNo: Int
    ): Page<WiseSaying> {
        return when(keywordType) {
            "author" -> findByAuthorLike("%$keyword%")
            else -> findByAuthorContent("%$keyword%")
        }.let {
            val content = it
                .drop((pageNo - 1) * itemsPerPage)
                .take(itemsPerPage)

            Page(it.size, itemsPerPage, pageNo, keywordType, keyword, content)
        }
    }
}