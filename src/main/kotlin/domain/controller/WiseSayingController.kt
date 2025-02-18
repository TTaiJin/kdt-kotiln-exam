package com.ll.domain.controller

import com.ll.global.bean.SingletonScope
import com.ll.global.util.CommandUtil

class WiseSayingController {

    private val wiseSayingService = SingletonScope.wiseSayingService

    fun addWiseSaying() {
        print("명언: ")
        val content = readln().takeIf { it.isNotBlank() } ?: run {
            println("명언은 공백을 입력할 수 없습니다.")
            return
        }

        print("작가: ")
        val author = readln().takeIf { it.isNotBlank() } ?: run {
            println("작가명은 공백을 입력할 수 없습니다.")
            return
        }

        val createdWiseSaying = wiseSayingService.createWiseSaying(content, author)

        println("${createdWiseSaying.id}번 명언이 등록되었습니다.")
    }

    fun getWiseSayings(command: CommandUtil) {
        val keywordType = command.getParamValue("keywordType", "content")
        val keyword = command.getParamValue("keyword", "")

        val itemsPerPage = 5
        val pageNo: Int = command.getParamValueAsInt("page", 1)

        val wiseSayingPage = if (keyword.isNotBlank()) {
            wiseSayingService.findByKeywordPaged(keywordType, keyword, itemsPerPage, pageNo)
        } else {
            wiseSayingService.findAllPaged(itemsPerPage, pageNo)
        }

        if (keyword.isNotBlank()) {
            println("----------------------")
            println("검색타입 : $keywordType")
            println("검색어 : $keyword")
            println("----------------------")
        }

        println("번호 / 작가 / 명언")

        println("----------------------")

        wiseSayingPage.content.forEach {
            println("${it.id} / ${it.author} / ${it.content}")
        }

        print("페이지 : ")

        val pageMenu = (1..wiseSayingPage.totalPages)
            .joinToString(" ") {
                if (it == pageNo) "[$it]" else it.toString()
            }

        println(pageMenu)
    }

    fun deleteWiseSaying(id: Int?) {
        if (id == null) {
            println("잘못입력했습니다.")
            return
        }
        val isDeleted = wiseSayingService.deleteWiseSaying(id)
        if (isDeleted == true) {
            println("${id}번 명언이 삭제되었습니다.")
        } else {
            println("${id}번 명언은 존재하지 않습니다.")
        }
    }

    fun modifyWiseSaying(id: Int?) {
        if (id == null) {
            println("잘못입력했습니다.")
            return
        }
        val wiseSaying = wiseSayingService.getWiseSayingById(id)

        if (wiseSaying == null) {
            println("해당 번호의 명언은 존재하지 않습니다.")
            return
        }

        println("명언(기존) : ${wiseSaying.content}")
        print("명언 : ")
        val content = readlnOrNull()!!

        println("작가(기존) : ${wiseSaying.author}")
        print("작가 : ")
        val author = readlnOrNull()!!

        wiseSayingService.modifyWiseSaying(wiseSaying, content, author)

        println("${id}번 명언이 수정되었습니다.")
    }

    fun buildWiseSayings() {
        wiseSayingService.build()

        println("data.json 파일의 내용이 갱신되었습니다.")
    }
}