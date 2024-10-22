package co.ivanovpv.githubdata.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory


object LoggerManager {

    fun debug(any: Any, message: String) {
        val logger: Logger = LoggerFactory.getLogger(any.javaClass.name)
        logger.info(message)
    }

}