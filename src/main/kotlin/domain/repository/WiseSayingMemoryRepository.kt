package com.ll.domain.repository

import com.ll.domain.entity.WiseSaying

class WiseSayingMemoryRepository : WiseSayingRepository {

    private val wiseSayings: MutableList<WiseSaying> = mutableListOf()
    private var lastId: Int = 0


    override fun save(wiseSaying: WiseSaying) : WiseSaying {
        if(wiseSaying.isNew()) {
            wiseSaying.setId(++lastId)
            wiseSayings.add(wiseSaying)
            return wiseSaying
        }
        return wiseSaying
    }

    override fun findAll() : List<WiseSaying> {
        return wiseSayings.toList()
    }

    override fun findById(id: Int) : WiseSaying? {
        return wiseSayings.firstOrNull {it.getId() == id}
    }

    override fun delete(id: Int): Boolean {
        return wiseSayings.remove(findById(id))
    }

    override fun clear() {
        lastId = 0
        wiseSayings.clear()
    }
}