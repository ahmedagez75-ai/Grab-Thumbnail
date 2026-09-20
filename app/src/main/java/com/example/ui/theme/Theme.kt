package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme =
  darkColorScheme(
    primary = PomegranateRedLight,
    onPrimary = Color.White,
    primaryContainer = PomegranateRedContainer,
    onPrimaryContainer = Color(0xFFFFDAD9),
    secondary = AvocadoGreenLight,
    onSecondary = Color.Black,
    secondaryContainer = AvocadoGreenContainer,
    onSecondaryContainer = AvocadoGreenContainerLight,
    background = DarkBackground,
    onBackground = DarkTextPrimary,
    surface = DarkSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkTextSecondary,
    outline = DarkBorder,
  )

private val LightColorScheme =
  lightColorScheme(
    primary = PomegranateRed,
    onPrimary = Color.White,
    primaryContainer = PomegranateRedContainerLight,
    onPrimaryContainer = PomegranateRedDark,
    secondary = AvocadoGreen,
    onSecondary = Color.White,
    secondaryContainer = AvocadoGreenContainerLight,
    onSecondaryContainer = AvocadoGreenDark,
    background = OffWhiteBackground,
    onBackground = LightTextPrimary,
    surface = LightSurface,
    onSurface = LightTextPrimary,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightTextSecondary,
    outline = LightBorder,
  )

@Composable
fun ThumbGrabTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}

