package com.ll

import com.ll.global.bean.SingletonScope
import com.ll.global.util.CommandUtil

class App {
    fun run() {
        var isRun = true
        val wiseSayingController = SingletonScope.wiseSayingController

        println("== 명언 앱 ==")
        while (isRun) {
            print("입력: ")
            val input = readlnOrNull()!!.trim()

            val command = CommandUtil(input)

            when (command.action) {
                "종료" -> {
                    println("앱을 종료합니다.")
                    isRun = false
                }
                "등록" -> wiseSayingController.addWiseSaying()
                "목록" -> wiseSayingController.getWiseSayings(command)
                "삭제" -> wiseSayingController.deleteWiseSaying(
                    command.getParamValueAsInt("id", 0)
                )
                "수정" -> wiseSayingController.modifyWiseSaying(
                    command.getParamValueAsInt("id", 0)
                )
                "빌드" -> wiseSayingController.buildWiseSayings()
            }
        }
    }
}