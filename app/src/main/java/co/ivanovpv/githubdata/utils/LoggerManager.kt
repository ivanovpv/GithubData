package co.ivanovpv.githubdata.utils

import com.google.gson.GsonBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory


object LoggerManager {
    private val gson by lazy { GsonBuilder().setPrettyPrinting().serializeNulls().create() }

    fun debug(any: Any, message: String) {
        val logger: Logger = LoggerFactory.getLogger(any.javaClass.name)
        logger.info(message)
    }

    fun debugJson(any: Any, message: String, jsonObject: Any) {
        val logger: Logger = LoggerFactory.getLogger(any.javaClass.name)
        val json = gson.toJson(jsonObject)
        logger.info("$message json=$json")
    }
}