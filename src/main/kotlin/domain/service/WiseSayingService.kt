package com.ll.domain.service

import com.ll.domain.entity.WiseSaying
import com.ll.global.bean.SingletonScope

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
}
