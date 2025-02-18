package com.ll.domain.service

import com.ll.domain.entity.WiseSaying
import com.ll.global.bean.SingletonScope
import com.ll.global.dto.Page

class WiseSayingService {

    private val wiseSayingRepository = SingletonScope.wiseSayingRepository

    fun createWiseSaying(content: String, author: String): WiseSaying {
        val wiseSaying = WiseSaying(content = content, author = author)
        return wiseSayingRepository.save(wiseSaying)
    }

    fun getWiseSayings(): List<WiseSaying> {
        return wiseSayingRepository.findAll().reversed()
    }

    fun getWiseSayingById(id: Int): WiseSaying? {
        return wiseSayingRepository.findById(id)
    }

    fun deleteWiseSaying(id: Int): Boolean? {
        return wiseSayingRepository.delete(id)
    }

    fun modifyWiseSaying(wiseSaying: WiseSaying, content: String, author: String) {
        wiseSaying.updateWiseSaying(content, author)
        wiseSayingRepository.save(wiseSaying)
    }

    fun build() {
        wiseSayingRepository.build()
    }

    fun findByKeyword(keywordType: String, keyword: String): List<WiseSaying> {
        return when(keywordType) {
            "author" -> wiseSayingRepository.findByAuthorLike("%$keyword%")
            else -> wiseSayingRepository.findByAuthorContent("%$keyword%")
        }
    }

    fun findAllPaged(itemsPerPage: Int, pageNo: Int): Page<WiseSaying> {
        return wiseSayingRepository.findAllPaged(itemsPerPage, pageNo)
    }

    fun findByKeywordPaged(keywordType: String, keyword: String, itemsPerPage: Int, pageNo: Int): Page<WiseSaying> {
        return wiseSayingRepository.findByKeywordPaged(keywordType, keyword, itemsPerPage, pageNo)
    }
}
