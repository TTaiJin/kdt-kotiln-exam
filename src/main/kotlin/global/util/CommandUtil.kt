package com.ll.global.util

class CommandUtil(command: String) {
    val action: String
    private val paramMap = mutableMapOf<String, String>()

    init {
        val commandBits = command.split("?", limit = 2)
        action = commandBits[0].trim()

        if (commandBits.size == 2) {
            val queryBits = commandBits[1].split("&")

            for (queryBit in queryBits) {
                val queryParamBits = queryBit.split("=")

                if (queryParamBits.size != 2) {
                    continue
                }

                val key = queryParamBits[0].trim()
                val value = queryParamBits[1].trim()

                paramMap[key] = value
            }
        }
    }

    private fun getParamValue (key: String): String? {
        return paramMap[key]
    }

    fun getParamValue(name: String, default: String): String {
        return getParamValue(name) ?: default
    }

    fun getParamValueAsInt(key: String): Int? {
        val value = getParamValue(key) ?: return null
        return try {
            value.toInt()
        } catch (e: NumberFormatException) {
            null
        }
    }
}