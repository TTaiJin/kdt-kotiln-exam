package com.ll.domain.repository

import com.ll.domain.entity.WiseSaying
import com.ll.global.config.AppConfig
import java.nio.file.Path

class WiseSayingFileRepository : WiseSayingRepository {

    val tableDirPath: Path
        get() {
            return AppConfig.dbDirPath.resolve("WiseSaying")
        }

    override fun save(wiseSaying: WiseSaying): WiseSaying {
        if (wiseSaying.isNew()) wiseSaying.id = genNextId()

        saveOnDisk(wiseSaying)

        return wiseSaying
    }

    override fun findAll(): List<WiseSaying> {
        return tableDirPath
            .toFile()
            .listFiles()
            ?.filter { it.name.endsWith(".json") }
            ?.map { it.readText() }
            ?.map(WiseSaying.Companion::fromJsonStr)
            .orEmpty()
    }

    override fun findById(id: Int): WiseSaying? {
        return tableDirPath
            .resolve("$id.json")
            .toFile()
            .takeIf { it.exists() }
            ?.readText()
            ?.let(WiseSaying.Companion::fromJsonStr)
    }

    override fun delete(id: Int): Boolean? {
        return tableDirPath
            .resolve("$id.json")
            .toFile()
            .takeIf { it.exists() }
            ?.delete()
    }

    override fun clear() {
        tableDirPath.toFile().deleteRecursively()
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

    internal fun saveLastId(lastId: Int) {
        mkTableDirsIfNotExists()

        tableDirPath.resolve("lastId.txt")
            .toFile()
            .writeText(lastId.toString())
    }

    internal fun loadLastId(): Int {
        return try {
            tableDirPath.resolve("lastId.txt")
                .toFile()
                .readText()
                .toInt()
        } catch (e: Exception) {
            0
        }
    }

    private fun genNextId(): Int {
        return (loadLastId() + 1).also {
            saveLastId(it)
        }
    }
}