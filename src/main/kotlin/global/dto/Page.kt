package com.ll.global.dto

import kotlin.math.ceil

data class Page<T>(
    val totalItems: Int,
    val itemsPerPage: Int,
    val pageNo: Int,
    val keywordType: String,
    val keyWord: String,
    val content: List<T>
) {
    val totalPages = ceil(totalItems.toDouble() / itemsPerPage).toInt()
}
