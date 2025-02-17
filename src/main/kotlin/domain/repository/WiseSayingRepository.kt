package com.ll.domain.repository

import com.ll.domain.entity.WiseSaying

interface WiseSayingRepository {

    fun save(wiseSaying: WiseSaying) : WiseSaying

    fun findAll() : List<WiseSaying>

    fun findById(id: Int) : WiseSaying?

    fun delete(id: Int): Boolean

    fun clear()
}