package com.ll.domain.repository

import com.ll.domain.entity.WiseSaying
import com.ll.global.config.AppConfig
import java.nio.file.Path

class WiseSayingFileRepository : WiseSayingRepository {

    private val wiseSayings: MutableList<WiseSaying> = mutableListOf()
    private var lastId: Int = 0

    val tableDirPath: Path
        get() {
            return AppConfig.dbDirPath.resolve("WiseSaying")
        }

    override fun save(wiseSaying: WiseSaying): WiseSaying {
        if (wiseSaying.isNew()) {
            wiseSaying.id = ++lastId
            wiseSayings.add(wiseSaying)
        }

        saveOnDisk(wiseSaying)

        return wiseSaying
    }

    override fun findAll(): List<WiseSaying> {
        return wiseSayings.toList()
    }

    override fun findById(id: Int): WiseSaying? {
        return wiseSayings.firstOrNull { it.id == id }
    }

    override fun delete(id: Int): Boolean {
        return wiseSayings.remove(findById(id))
    }

    override fun clear() {
        lastId = 0
        wiseSayings.clear()
    }

    private fun saveOnDisk(wiseSaying: WiseSaying) {
        mkTableDirsIfNotExists()

        val wiseSayingFile = tableDirPath.resolve("${wiseSaying.id}.json")
        wiseSayingFile.toFile().writeText(wiseSaying.jsonStr)
    }

    private fun mkTableDirsIfNotExists() {
        tableDirPath.toFile().run {
            if (!exists()) {
                mkdirs()
            }
        }
    }
}