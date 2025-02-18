package com.ll.domain.repository

import com.ll.domain.entity.WiseSaying
import com.ll.global.dto.Page

interface WiseSayingRepository {

    fun save(wiseSaying: WiseSaying) : WiseSaying

    fun findAll() : List<WiseSaying>

    fun findById(id: Int) : WiseSaying?

    fun delete(id: Int) : Boolean?

    fun clear()

    fun build()

    fun findByAuthorLike(authorLike: String) : List<WiseSaying>

    fun findByAuthorContent(contentLike: String) : List<WiseSaying>

    fun findAllPaged(itemsPerPage: Int, pageNo: Int): Page<WiseSaying>

    fun findByKeywordPaged(keywordType: String, keyword: String, itemsPerPage: Int, pageNo: Int): Page<WiseSaying>
}