package com.ll.domain.repository

import com.ll.domain.entity.WiseSaying

class WiseSayingRepository {

    private val wiseSayings: MutableList<WiseSaying> = mutableListOf()
    private var lastId: Int = 0


    fun save(wiseSaying: WiseSaying) : WiseSaying {
        if(wiseSaying.isNew()) {
            wiseSaying.setId(++lastId)
            wiseSayings.add(wiseSaying)
            return wiseSaying
        }
        return wiseSaying
    }

    fun findAll() : List<WiseSaying> {
        return wiseSayings.toList()
    }

    fun findById(id: Int) : WiseSaying? {
        return wiseSayings.getOrNull(id - 1)
    }
}