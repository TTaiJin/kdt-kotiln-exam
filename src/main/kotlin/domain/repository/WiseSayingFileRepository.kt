package com.ll.domain.repository

import com.ll.domain.entity.WiseSaying
import com.ll.global.config.AppConfig
import com.ll.global.util.JsonUtil
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
            ?.filter { it.name != "data.json" }
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

    override fun build() {
        mkTableDirsIfNotExists()

        val mapList = findAll()
            .map(WiseSaying::map)

        JsonUtil.toString(mapList)
            .let {
                tableDirPath
                    .resolve("data.json")
                    .toFile()
                    .writeText(it)
            }
    }

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