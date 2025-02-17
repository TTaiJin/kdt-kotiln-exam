package com.ll.domain.controller

import com.ll.domain.service.WiseSayingService

class WiseSayingController {

    private val wiseSayingService = WiseSayingService()

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

        println("${createdWiseSaying.getId()}번 명언이 등록되었습니다.")
    }

    fun getWiseSayings() {
        val wiseSayings = wiseSayingService.getWiseSayings()
        for (wiseSaying in wiseSayings) {
            println("${wiseSaying.getId()} / ${wiseSaying.getAuthor()} / ${wiseSaying.getContent()}")
        }
    }


    fun deleteWiseSaying(id: Int?) {
        if (id == null) {
            println("잘못입력했습니다.")
            return
        }
        val isDeleted = wiseSayingService.deleteWiseSaying(id)
        if (isDeleted) {
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

        println("명언(기존) : ${wiseSaying.getContent()}")
        print("명언 : ")
        val content = readlnOrNull()!!

        println("작가(기존) : ${wiseSaying.getAuthor()}")
        print("작가 : ")
        val author = readlnOrNull()!!

        wiseSayingService.modifyWiseSaying(wiseSaying, content, author)

        println("${id}번 명언이 수정되었습니다.")
    }
}