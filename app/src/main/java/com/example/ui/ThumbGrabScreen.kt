package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.AppHeader
import com.example.ui.components.DownloaderCard
import com.example.ui.components.FaqSection
import com.example.ui.components.FeaturesSection
import com.example.ui.components.FooterSection
import com.example.ui.components.HeroSection
import com.example.ui.components.HowItWorksSection
import com.example.ui.components.LightboxModal
import com.example.ui.components.ResultCard
import com.example.ui.components.TrustSection
import com.example.ui.theme.ThumbGrabTheme
import kotlinx.coroutines.launch

@Composable
fun ThumbGrabApp(
    viewModel: ThumbGrabViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    ThumbGrabTheme(darkTheme = uiState.isDarkMode) {
        val layoutDirection = if (uiState.isArabic) LayoutDirection.Rtl else LayoutDirection.Ltr

        CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
            ThumbGrabScreen(
                uiState = uiState,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun ThumbGrabScreen(
    uiState: ThumbGrabUiState,
    viewModel: ThumbGrabViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Feedback message display
    LaunchedEffect(uiState.feedbackMessage) {
        uiState.feedbackMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.dismissFeedback()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            AppHeader(
                isArabic = uiState.isArabic,
                isDarkMode = uiState.isDarkMode,
                onToggleLanguage = { viewModel.toggleLanguage() },
                onToggleTheme = { viewModel.toggleTheme() },
                onNavigateToSection = { section ->
                    coroutineScope.launch {
                        when (section) {
                            "home" -> scrollState.animateScrollTo(0)
                            "how_it_works" -> scrollState.animateScrollTo(750)
                            "faq" -> scrollState.animateScrollTo(1600)
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 700.dp) // Max-width SaaS constraint
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Hero Section
                HeroSection(isArabic = uiState.isArabic)

                // Main Downloader Tool
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    DownloaderCard(
                        urlInput = uiState.urlInput,
                        isLoading = uiState.isLoading,
                        errorMessage = uiState.errorMessage,
                        isArabic = uiState.isArabic,
                        onUrlChange = { viewModel.onUrlInputChanged(it) },
                        onPaste = { viewModel.onPaste(it) },
                        onClear = { viewModel.onClearInput() },
                        onExtract = { viewModel.extractThumbnail() },
                        onSampleClick = { viewModel.loadSample() }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Result Area
                AnimatedVisibility(
                    visible = uiState.extractedVideo != null && uiState.selectedOption != null,
                    enter = fadeIn() + slideInVertically(initialOffsetY = { 80 }),
                    exit = fadeOut()
                ) {
                    if (uiState.extractedVideo != null && uiState.selectedOption != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            ResultCard(
                                videoData = uiState.extractedVideo,
                                selectedOption = uiState.selectedOption,
                                isSaving = uiState.isSaving,
                                isArabic = uiState.isArabic,
                                onSelectOption = { viewModel.selectOption(it) },
                                onDownload = { viewModel.downloadThumbnail(context) },
                                onCopyUrl = { viewModel.copyImageUrl(context) },
                                onOpenOriginal = { viewModel.openOriginalImage(context) },
                                onOpenLightbox = { viewModel.setLightbox(true) }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Trust Badges
                TrustSection(isArabic = uiState.isArabic)

                Spacer(modifier = Modifier.height(12.dp))

                // How It Works
                HowItWorksSection(isArabic = uiState.isArabic)

                // Features
                FeaturesSection(isArabic = uiState.isArabic)

                // FAQ
                FaqSection(isArabic = uiState.isArabic)

                Spacer(modifier = Modifier.height(20.dp))

                // Footer
                FooterSection(
                    isArabic = uiState.isArabic,
                    onNavigateToSection = { section ->
                        coroutineScope.launch {
                            when (section) {
                                "home" -> scrollState.animateScrollTo(0)
                                "how_it_works" -> scrollState.animateScrollTo(750)
                                "faq" -> scrollState.animateScrollTo(1600)
                            }
                        }
                    }
                )
            }
        }

        // Fullscreen Lightbox Modal
        if (uiState.isLightboxOpen && uiState.extractedVideo != null && uiState.selectedOption != null) {
            LightboxModal(
                videoData = uiState.extractedVideo,
                selectedOption = uiState.selectedOption,
                isSaving = uiState.isSaving,
                isArabic = uiState.isArabic,
                onDownload = { viewModel.downloadThumbnail(context) },
                onClose = { viewModel.setLightbox(false) }
            )
        }
    }
}
