package com.ll.global.bean

import com.ll.domain.controller.WiseSayingController
import com.ll.domain.repository.WiseSayingMemoryRepository
import com.ll.domain.service.WiseSayingService

object SingletonScope {
    val wiseSayingController by lazy { WiseSayingController() }
    val wiseSayingService by lazy { WiseSayingService() }
    val wiseSayingRepository by lazy { WiseSayingMemoryRepository() }
}