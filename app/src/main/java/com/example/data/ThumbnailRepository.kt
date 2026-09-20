package com.example.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class ThumbnailRepository(
    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(6, TimeUnit.SECONDS)
        .readTimeout(6, TimeUnit.SECONDS)
        .build()
) {

    suspend fun getThumbnailData(videoId: String): Result<ExtractedVideo> = withContext(Dispatchers.IO) {
        try {
            // 1. Fetch title and author via official public YouTube oEmbed API
            val oembedData = fetchVideoMeta(videoId)
            val title = oembedData.first.ifBlank { "YouTube Video ($videoId)" }
            val author = oembedData.second.ifBlank { "YouTube Creator" }

            // 2. Candidate qualities in descending resolution
            val candidates = listOf(
                Candidate(
                    key = "maxres",
                    labelAr = "أعلى جودة (1080p / 720p)",
                    labelEn = "Maximum HD (1280 × 720)",
                    resolution = "1280 × 720",
                    tagAr = "أعلى جودة متاحة",
                    tagEn = "Highest Available Quality",
                    url = "https://img.youtube.com/vi/$videoId/maxresdefault.jpg"
                ),
                Candidate(
                    key = "sd",
                    labelAr = "جودة عالية (480p)",
                    labelEn = "High Quality (640 × 480)",
                    resolution = "640 × 480",
                    tagAr = "جودة عالية",
                    tagEn = "High Quality",
                    url = "https://img.youtube.com/vi/$videoId/sddefault.jpg"
                ),
                Candidate(
                    key = "hq",
                    labelAr = "جودة متوسطة (360p)",
                    labelEn = "Medium Quality (480 × 360)",
                    resolution = "480 × 360",
                    tagAr = "جودة متوسطة",
                    tagEn = "Medium Quality",
                    url = "https://img.youtube.com/vi/$videoId/hqdefault.jpg"
                ),
                Candidate(
                    key = "mq",
                    labelAr = "جودة مقبولة (180p)",
                    labelEn = "Standard Quality (320 × 180)",
                    resolution = "320 × 180",
                    tagAr = "جودة عادية",
                    tagEn = "Standard Quality",
                    url = "https://img.youtube.com/vi/$videoId/mqdefault.jpg"
                ),
                Candidate(
                    key = "default",
                    labelAr = "جودة منخفضة (90p)",
                    labelEn = "Low Quality (120 × 90)",
                    resolution = "120 × 90",
                    tagAr = "جودة منخفضة",
                    tagEn = "Low Quality",
                    url = "https://img.youtube.com/vi/$videoId/default.jpg"
                )
            )

            // 3. Test which thumbnails actually exist on YouTube servers
            val availableOptions = mutableListOf<ThumbnailOption>()
            var firstValidCandidate: Candidate? = null

            for (candidate in candidates) {
                val exists = checkThumbnailExists(candidate.url)
                if (exists) {
                    if (firstValidCandidate == null) {
                        firstValidCandidate = candidate
                    }
                    availableOptions.add(
                        ThumbnailOption(
                            key = candidate.key,
                            labelAr = candidate.labelAr,
                            labelEn = candidate.labelEn,
                            resolution = candidate.resolution,
                            qualityTagAr = candidate.tagAr,
                            qualityTagEn = candidate.tagEn,
                            url = candidate.url,
                            isHighest = false,
                            isAvailable = true
                        )
                    )
                }
            }

            // Fallback: If network check was restricted or offline, use standard hqdefault
            val primaryCandidate = firstValidCandidate ?: candidates.first { it.key == "hq" }
            if (availableOptions.isEmpty()) {
                candidates.forEach { c ->
                    availableOptions.add(
                        ThumbnailOption(
                            key = c.key,
                            labelAr = c.labelAr,
                            labelEn = c.labelEn,
                            resolution = c.resolution,
                            qualityTagAr = c.tagAr,
                            qualityTagEn = c.tagEn,
                            url = c.url,
                            isHighest = c.key == primaryCandidate.key,
                            isAvailable = true
                        )
                    )
                }
            }

            val highestOption = ThumbnailOption(
                key = primaryCandidate.key,
                labelAr = primaryCandidate.labelAr,
                labelEn = primaryCandidate.labelEn,
                resolution = primaryCandidate.resolution,
                qualityTagAr = primaryCandidate.tagAr,
                qualityTagEn = primaryCandidate.tagEn,
                url = primaryCandidate.url,
                isHighest = true,
                isAvailable = true
            )

            // Update highest flag in list
            val finalOptions = availableOptions.map { opt ->
                if (opt.key == highestOption.key) opt.copy(isHighest = true) else opt
            }

            val sanitized = sanitizeFilename(title)

            Result.success(
                ExtractedVideo(
                    videoId = videoId,
                    title = title,
                    author = author,
                    activeThumbnail = highestOption,
                    availableOptions = finalOptions,
                    sanitizedFilename = "$sanitized-Thumbnail.jpg",
                    originalVideoUrl = "https://www.youtube.com/watch?v=$videoId"
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun fetchVideoMeta(videoId: String): Pair<String, String> {
        return try {
            val url = "https://www.youtube.com/oembed?url=https://www.youtube.com/watch?v=$videoId&format=json"
            val request = Request.Builder()
                .url(url)
                .header("User-Agent", "Mozilla/5.0 ThumbGrab/1.0")
                .get()
                .build()

            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    val body = response.body?.string() ?: ""
                    val json = JSONObject(body)
                    val title = json.optString("title", "")
                    val author = json.optString("author_name", "")
                    Pair(title, author)
                } else {
                    Pair("", "")
                }
            }
        } catch (e: Exception) {
            Pair("", "")
        }
    }

    private fun checkThumbnailExists(url: String): Boolean {
        return try {
            val request = Request.Builder()
                .url(url)
                .head()
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return false
                val contentLength = response.header("Content-Length")?.toLongOrNull() ?: 0L
                // YouTube returns a 1097-byte blank/grey 404 placeholder for missing maxresdefault
                // Valid maxres / sd thumbnails are typically 15,000 to 200,000+ bytes.
                if (url.contains("maxresdefault") && contentLength in 1..2048) {
                    return false
                }
                true
            }
        } catch (e: Exception) {
            // In case HEAD is blocked, fallback to true for standard resolutions
            !url.contains("maxresdefault")
        }
    }

    companion object {
        fun sanitizeFilename(title: String): String {
            if (title.isBlank()) return "YouTube-Video"

            // Keep Arabic characters (\u0600-\u06FF, \u0750-\u077F, \u08A0-\u08FF),
            // English letters, digits, dashes, and underscores.
            // Replace invalid filesystem characters (\ / : * ? " < > | and others) with dashes.
            val cleaned = StringBuilder()
            var lastWasDash = false

            for (ch in title) {
                val isArabic = ch in '\u0600'..'\u06FF' || ch in '\u0750'..'\u077F' || ch in '\u08A0'..'\u08FF'
                val isLatin = ch in 'a'..'z' || ch in 'A'..'Z'
                val isDigit = ch in '0'..'9'
                val isDashOrUnderscore = ch == '-' || ch == '_'

                if (isArabic || isLatin || isDigit) {
                    cleaned.append(ch)
                    lastWasDash = false
                } else if (ch.isWhitespace() || isDashOrUnderscore) {
                    if (!lastWasDash && cleaned.isNotEmpty()) {
                        cleaned.append('-')
                        lastWasDash = true
                    }
                }
            }

            val result = cleaned.toString().trim('-')
            return if (result.isBlank()) "YouTube-Video" else result.take(80)
        }
    }

    private data class Candidate(
        val key: String,
        val labelAr: String,
        val labelEn: String,
        val resolution: String,
        val tagAr: String,
        val tagEn: String,
        val url: String
    )
}
