package com.example.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ErrorType
import com.example.data.ExtractedVideo
import com.example.data.ThumbnailOption
import com.example.data.ThumbnailRepository
import com.example.data.UrlParseResult
import com.example.data.YouTubeUrlParser
import com.example.util.ImageDownloader
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ThumbGrabUiState(
    val urlInput: String = "",
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val extractedVideo: ExtractedVideo? = null,
    val selectedOption: ThumbnailOption? = null,
    val errorMessage: String? = null,
    val feedbackMessage: String? = null,
    val isArabic: Boolean = true,
    val isDarkMode: Boolean = false,
    val isLightboxOpen: Boolean = false
)

class ThumbGrabViewModel(
    private val repository: ThumbnailRepository = ThumbnailRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(ThumbGrabUiState())
    val uiState: StateFlow<ThumbGrabUiState> = _uiState.asStateFlow()

    fun onUrlInputChanged(newUrl: String) {
        _uiState.update { it.copy(urlInput = newUrl, errorMessage = null) }
    }

    fun onClearInput() {
        _uiState.update { it.copy(urlInput = "", errorMessage = null) }
    }

    fun onPaste(text: String) {
        if (text.isNotBlank()) {
            _uiState.update { it.copy(urlInput = text.trim(), errorMessage = null) }
            extractThumbnail()
        }
    }

    fun loadSample() {
        val sample = "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
        _uiState.update { it.copy(urlInput = sample, errorMessage = null) }
        extractThumbnail()
    }

    fun extractThumbnail() {
        val input = _uiState.value.urlInput
        val isAr = _uiState.value.isArabic

        val parseResult = YouTubeUrlParser.parse(input)
        when (parseResult) {
            is UrlParseResult.Error -> {
                val errorMsg = when (parseResult.errorType) {
                    ErrorType.EMPTY_INPUT -> Strings.errEmptyInput(isAr)
                    ErrorType.INVALID_URL -> Strings.errInvalidUrl(isAr)
                    ErrorType.UNSUPPORTED_URL -> Strings.errUnsupportedUrl(isAr)
                    ErrorType.NO_VIDEO_ID -> Strings.errNoThumbnail(isAr)
                }
                _uiState.update { it.copy(errorMessage = errorMsg) }
                return
            }
            is UrlParseResult.Success -> {
                val videoId = parseResult.videoId
                _uiState.update { it.copy(isLoading = true, errorMessage = null) }

                viewModelScope.launch {
                    val result = repository.getThumbnailData(videoId)
                    result.fold(
                        onSuccess = { videoData ->
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    extractedVideo = videoData,
                                    selectedOption = videoData.activeThumbnail,
                                    feedbackMessage = Strings.successBadge(it.isArabic)
                                )
                            }
                        },
                        onFailure = {
                            _uiState.update { state ->
                                state.copy(
                                    isLoading = false,
                                    errorMessage = Strings.errNetwork(state.isArabic)
                                )
                            }
                        }
                    )
                }
            }
        }
    }

    fun selectOption(option: ThumbnailOption) {
        _uiState.update { it.copy(selectedOption = option) }
    }

    fun downloadThumbnail(context: Context) {
        val activeOption = _uiState.value.selectedOption ?: _uiState.value.extractedVideo?.activeThumbnail ?: return
        val filename = _uiState.value.extractedVideo?.sanitizedFilename ?: "YouTube-Thumbnail.jpg"
        val isAr = _uiState.value.isArabic

        _uiState.update { it.copy(isSaving = true) }
        viewModelScope.launch {
            val result = ImageDownloader.saveImageToGallery(context, activeOption.url, filename)
            result.fold(
                onSuccess = {
                    _uiState.update {
                        it.copy(isSaving = false, feedbackMessage = Strings.feedbackSaved(isAr))
                    }
                },
                onFailure = {
                    _uiState.update {
                        it.copy(isSaving = false, errorMessage = Strings.feedbackSaveFailed(isAr))
                    }
                }
            )
        }
    }

    fun copyImageUrl(context: Context) {
        val activeOption = _uiState.value.selectedOption ?: _uiState.value.extractedVideo?.activeThumbnail ?: return
        ImageDownloader.copyToClipboard(context, activeOption.url)
        _uiState.update { it.copy(feedbackMessage = Strings.feedbackCopied(it.isArabic)) }
    }

    fun openOriginalImage(context: Context) {
        val activeOption = _uiState.value.selectedOption ?: _uiState.value.extractedVideo?.activeThumbnail ?: return
        ImageDownloader.openUrl(context, activeOption.url)
    }

    fun toggleLanguage() {
        _uiState.update { it.copy(isArabic = !it.isArabic) }
    }

    fun toggleTheme() {
        _uiState.update { it.copy(isDarkMode = !it.isDarkMode) }
    }

    fun setLightbox(open: Boolean) {
        _uiState.update { it.copy(isLightboxOpen = open) }
    }

    fun dismissFeedback() {
        _uiState.update { it.copy(feedbackMessage = null) }
    }

    fun dismissError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}
