package com.example.data

import java.net.URI

sealed class UrlParseResult {
    data class Success(val videoId: String) : UrlParseResult()
    data class Error(val errorType: ErrorType) : UrlParseResult()
}

enum class ErrorType {
    EMPTY_INPUT,
    INVALID_URL,
    UNSUPPORTED_URL,
    NO_VIDEO_ID
}

object YouTubeUrlParser {
    private val VIDEO_ID_REGEX = Regex("^[a-zA-Z0-9_-]{11}$")
    
    private val ALLOWED_HOSTS = setOf(
        "youtube.com",
        "www.youtube.com",
        "m.youtube.com",
        "youtu.be",
        "music.youtube.com",
        "www.music.youtube.com"
    )

    fun parse(input: String): UrlParseResult {
        val trimmed = input.trim()
        if (trimmed.isEmpty()) {
            return UrlParseResult.Error(ErrorType.EMPTY_INPUT)
        }

        // If user directly entered an 11-char YouTube video ID
        if (VIDEO_ID_REGEX.matches(trimmed)) {
            return UrlParseResult.Success(trimmed)
        }

        val urlString = if (!trimmed.startsWith("http://") && !trimmed.startsWith("https://")) {
            "https://$trimmed"
        } else {
            trimmed
        }

        val uri = try {
            URI(urlString)
        } catch (e: Exception) {
            return UrlParseResult.Error(ErrorType.INVALID_URL)
        }

        val host = uri.host?.lowercase() ?: return UrlParseResult.Error(ErrorType.INVALID_URL)

        val isAllowedHost = ALLOWED_HOSTS.any { allowed ->
            host == allowed || host.endsWith(".$allowed")
        }

        if (!isAllowedHost) {
            return UrlParseResult.Error(ErrorType.UNSUPPORTED_URL)
        }

        val path = uri.path ?: ""
        val query = uri.query ?: ""

        val videoId = when {
            // youtu.be/<id>
            host == "youtu.be" || host.endsWith(".youtu.be") -> {
                val candidate = path.removePrefix("/").split("/").firstOrNull()?.split("?")?.firstOrNull()
                candidate
            }
            // youtube.com/shorts/<id>
            path.contains("/shorts/") -> {
                path.substringAfter("/shorts/").split("/").firstOrNull()?.split("?")?.firstOrNull()
            }
            // youtube.com/embed/<id>
            path.contains("/embed/") -> {
                path.substringAfter("/embed/").split("/").firstOrNull()?.split("?")?.firstOrNull()
            }
            // youtube.com/live/<id>
            path.contains("/live/") -> {
                path.substringAfter("/live/").split("/").firstOrNull()?.split("?")?.firstOrNull()
            }
            // youtube.com/v/<id>
            path.contains("/v/") -> {
                path.substringAfter("/v/").split("/").firstOrNull()?.split("?")?.firstOrNull()
            }
            // youtube.com/watch?v=<id>
            else -> {
                val params = query.split("&")
                params.firstOrNull { it.startsWith("v=") }?.substringAfter("v=")
            }
        }

        if (videoId != null && VIDEO_ID_REGEX.matches(videoId)) {
            return UrlParseResult.Success(videoId)
        }

        return UrlParseResult.Error(ErrorType.INVALID_URL)
    }
}
