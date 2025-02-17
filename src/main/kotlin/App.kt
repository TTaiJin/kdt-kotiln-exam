package com.ll

import com.ll.domain.controller.WiseSayingController
import com.ll.global.util.CommandUtil

class App {
    fun run() {
        var isRun = true
        val wiseSayingController = WiseSayingController()

        println("== 명언 앱 ==")
        while (isRun) {
            print("입력: ")
            val input = readlnOrNull()!!.trim()

            val command = CommandUtil(input)

            when (command.action) {
                "종료" -> isRun = false
                "등록" -> wiseSayingController.addWiseSaying()
                "목록" -> wiseSayingController.getWiseSayings()
                "삭제" -> wiseSayingController.deleteWiseSaying(
                    command.getParamValueAsInt("id")
                )
                "수정" -> wiseSayingController.modifyWiseSaying(
                    command.getParamValueAsInt("id")
                )
            }
        }
    }
}