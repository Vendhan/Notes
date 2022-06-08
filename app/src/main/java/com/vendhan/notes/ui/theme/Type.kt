package com.vendhan.notes.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.vendhan.notes.R

val PoppinsFont = FontFamily(
    Font(R.font.poppins_black, FontWeight.Black),
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_regular, FontWeight.Normal)
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = PoppinsFont
    ),
    displayMedium = TextStyle(
        fontFamily = PoppinsFont
    ),
    displaySmall = TextStyle(
        fontFamily = PoppinsFont
    ),
    headlineLarge = TextStyle(
        fontFamily = PoppinsFont
    ),
    headlineMedium = TextStyle(
        fontFamily = PoppinsFont
    ),
    headlineSmall = TextStyle(
        fontFamily = PoppinsFont
    ),
    titleLarge = TextStyle(
        fontFamily = PoppinsFont
    ),
    titleMedium = TextStyle(
        fontFamily = PoppinsFont
    ),
    titleSmall = TextStyle(
        fontFamily = PoppinsFont
    ),
    bodyLarge = TextStyle(
        fontFamily = PoppinsFont
    ),
    bodyMedium = TextStyle(
        fontFamily = PoppinsFont
    ),
    bodySmall = TextStyle(
        fontFamily = PoppinsFont
    ),
    labelLarge = TextStyle(
        fontFamily = PoppinsFont
    ),
    labelMedium = TextStyle(
        fontFamily = PoppinsFont
    ),
    labelSmall = TextStyle(
        fontFamily = PoppinsFont
    ),
)
