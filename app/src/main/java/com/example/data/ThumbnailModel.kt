package com.example.data

data class ThumbnailOption(
    val key: String,             // "maxres", "sd", "hq", "mq", "default"
    val labelAr: String,         // "أعلى جودة (HD)", "جودة عالية (SD)", etc.
    val labelEn: String,         // "Maximum Quality (HD)", "High Quality", etc.
    val resolution: String,      // "1280 × 720", "640 × 480", etc.
    val qualityTagAr: String,    // "أعلى جودة متاحة", "جودة متوسطة", etc.
    val qualityTagEn: String,    // "Highest Available Quality", "Medium Quality", etc.
    val url: String,
    val isHighest: Boolean = false,
    val isAvailable: Boolean = true
)

data class ExtractedVideo(
    val videoId: String,
    val title: String,
    val author: String,
    val activeThumbnail: ThumbnailOption,
    val availableOptions: List<ThumbnailOption>,
    val sanitizedFilename: String,
    val originalVideoUrl: String
)
