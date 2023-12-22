package co.ivanovpv.githubdata.app

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppConfiguration @Inject constructor(
) {
    companion object {
        val IS_NETWORK_LOGGING_ENABLED = true
        const val CONNECT_TIMEOUT = 60L
        const val READ_TIMEOUT = 60L
        const val WRITE_TIMEOUT = 60L
        const val CACHE_DIR = "http_cache"
        const val CACHE_SIZE = 50L * 1024L * 1024L // 50 MiB
    }


    fun getGithubBaseUrl(): String {
        return "https://api.github.com/"
    }
}