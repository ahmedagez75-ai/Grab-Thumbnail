package com.example

import com.example.data.ErrorType
import com.example.data.ThumbnailRepository
import com.example.data.UrlParseResult
import com.example.data.YouTubeUrlParser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class YouTubeUrlParserTest {

    @Test
    fun parseStandardWatchUrl() {
        val url = "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
        val result = YouTubeUrlParser.parse(url)
        assertTrue(result is UrlParseResult.Success)
        assertEquals("dQw4w9WgXcQ", (result as UrlParseResult.Success).videoId)
    }

    @Test
    fun parseStandardWatchUrlWithAdditionalParams() {
        val url = "https://www.youtube.com/watch?v=dQw4w9WgXcQ&t=42s&feature=share"
        val result = YouTubeUrlParser.parse(url)
        assertTrue(result is UrlParseResult.Success)
        assertEquals("dQw4w9WgXcQ", (result as UrlParseResult.Success).videoId)
    }

    @Test
    fun parseShortUrl() {
        val url = "https://youtu.be/dQw4w9WgXcQ?si=abcdef123456"
        val result = YouTubeUrlParser.parse(url)
        assertTrue(result is UrlParseResult.Success)
        assertEquals("dQw4w9WgXcQ", (result as UrlParseResult.Success).videoId)
    }

    @Test
    fun parseShortsUrl() {
        val url = "https://www.youtube.com/shorts/dQw4w9WgXcQ"
        val result = YouTubeUrlParser.parse(url)
        assertTrue(result is UrlParseResult.Success)
        assertEquals("dQw4w9WgXcQ", (result as UrlParseResult.Success).videoId)
    }

    @Test
    fun parseEmbedUrl() {
        val url = "https://www.youtube.com/embed/dQw4w9WgXcQ"
        val result = YouTubeUrlParser.parse(url)
        assertTrue(result is UrlParseResult.Success)
        assertEquals("dQw4w9WgXcQ", (result as UrlParseResult.Success).videoId)
    }

    @Test
    fun parseRawVideoId() {
        val id = "dQw4w9WgXcQ"
        val result = YouTubeUrlParser.parse(id)
        assertTrue(result is UrlParseResult.Success)
        assertEquals("dQw4w9WgXcQ", (result as UrlParseResult.Success).videoId)
    }

    @Test
    fun parseEmptyInput() {
        val result = YouTubeUrlParser.parse("   ")
        assertTrue(result is UrlParseResult.Error)
        assertEquals(ErrorType.EMPTY_INPUT, (result as UrlParseResult.Error).errorType)
    }

    @Test
    fun parseUnsupportedHost() {
        val result = YouTubeUrlParser.parse("https://vimeo.com/123456789")
        assertTrue(result is UrlParseResult.Error)
        assertEquals(ErrorType.UNSUPPORTED_URL, (result as UrlParseResult.Error).errorType)
    }

    @Test
    fun sanitizeFilenameEnglish() {
        val title = "7 Habits That Will Change Your Life"
        val sanitized = ThumbnailRepository.sanitizeFilename(title)
        assertEquals("7-Habits-That-Will-Change-Your-Life", sanitized)
    }

    @Test
    fun sanitizeFilenameArabic() {
        val title = "أفضل 5 نصائح لتطوير الذات والنجاح!"
        val sanitized = ThumbnailRepository.sanitizeFilename(title)
        assertEquals("أفضل-5-نصائح-لتطوير-الذات-والنجاح", sanitized)
    }
}
