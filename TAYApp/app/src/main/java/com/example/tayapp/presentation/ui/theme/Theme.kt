package com.example.tayapp.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = TAYColors(
    primary = dark50,
    defaultButton = dark50,
    background = dm_gray000,
    layer1 = dm_gray050,
    layer2 = dm_gray075,
    layer3 = dm_gray100,
    border = dm_gray200,
    fieldBorder = dm_gray300,
    disableIcon = dm_gray300,
    disableText = dm_gray400,
    subduedIcon = dm_gray400,
    controlBorder = dm_gray600,
    subduedText = dm_gray600,
    bodyText = dm_gray700,
    icon = dm_gray700,
    headText = dm_gray800,
    isDark = false,

)

private val LightColorPalette = TAYColors(
    primary = light50,
    defaultButton = light50,
    background = lm_gray000,
    layer1 = lm_gray050,
    layer2 = lm_gray075,
    layer3 = lm_gray100,
    border = lm_gray200,
    fieldBorder = lm_gray300,
    disableIcon = lm_gray300,
    disableText = lm_gray400,
    subduedIcon = lm_gray400,
    controlBorder = lm_gray600,
    subduedText = lm_gray600,
    bodyText = lm_gray700,
    icon = lm_gray700,
    headText = lm_gray800,
    isDark = true,

)

@Composable
fun TAYAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    ProvideTAYColors(colors = colors) {
        MaterialTheme(
            colors = debugColors(darkTheme),
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

/**
 * object TAYAppTheme 없으면
 * TAYAppTheme.colors. 호출불가
 */
object TAYAppTheme {
    val colors: TAYColors
        @Composable
        get() = LocalTAYColors.current
}

/**
 * custom Color Palette
 */
@Stable
class TAYColors(
    primary: Color,
    defaultButton: Color,
    background: Color,
    layer1: Color,
    layer2: Color,
    layer3: Color,
    border: Color,
    fieldBorder: Color,
    disableIcon: Color,
    disableText: Color,
    subduedIcon: Color,
    controlBorder: Color,
    subduedText: Color,
    bodyText: Color,
    icon: Color,
    headText: Color,
    isDark: Boolean
){
    var primary by mutableStateOf(primary)
        private set
    var defaultButton by mutableStateOf(defaultButton)
        private set
    var background by mutableStateOf(background)
        private set
    var layer1 by mutableStateOf(layer1)
        private set
    var layer2 by mutableStateOf(layer2)
        private set
    var layer3 by mutableStateOf(layer3)
        private set
    var border by mutableStateOf(border)
        private set
    var fieldBorder by mutableStateOf(fieldBorder)
        private set
    var disableIcon by mutableStateOf(disableIcon)
        private set
    var disableText by mutableStateOf(disableText)
        private set
    var subduedIcon by mutableStateOf(subduedIcon)
        private set
    var controlBorder by mutableStateOf(controlBorder)
        private set
    var subduedText by mutableStateOf(subduedText)
        private set
    var bodyText by mutableStateOf(bodyText)
        private set
    var icon by mutableStateOf(icon)
        private set
    var headText by mutableStateOf(headText)
        private set
    var isDark by mutableStateOf(isDark)
        private set

    /**
     * set(update) function
     */
    fun update(other: TAYColors){
        primary = other.primary
        defaultButton = other.defaultButton
        background= other.background
        layer1 = other.layer1
        layer2 = other.layer2
        layer2 = other.layer3
        border = other.border
        fieldBorder = other.fieldBorder
        disableIcon = other.disableIcon
        disableText = other.disableText
        subduedIcon = other.subduedIcon
        controlBorder = other.controlBorder
        subduedText = other.subduedText
        bodyText = other.bodyText
        icon = other.icon
        headText = other.headText
        isDark = other.isDark
    }

    /**
     * return TAYColors( get)
     */
    fun copy(): TAYColors = TAYColors(
        primary = primary,
        defaultButton = defaultButton,
        background = background,
        layer1 = layer1,
        layer2 = layer2,
        layer3 = layer3,
        border = border,
        fieldBorder = fieldBorder,
        disableIcon = disableIcon,
        disableText = disableText,
        subduedIcon = subduedIcon,
        controlBorder = controlBorder,
        subduedText = subduedText,
        bodyText = bodyText,
        icon = icon,
        headText = headText,
        isDark = isDark,
    )
}

@Composable
fun ProvideTAYColors(
    colors: TAYColors,
    content: @Composable () -> Unit
){
    val colorPalette = remember {
        colors.copy()
    }

    colorPalette.update(colors)
    CompositionLocalProvider(LocalTAYColors provides  colorPalette, content = content)
}

private val LocalTAYColors = staticCompositionLocalOf<TAYColors> {
    error("No ColorPalette")
}

/**
 * A Material [Colors] implementation which sets all colors to [debugColor] to discourage usage of
 * [MaterialTheme.colors] in preference to [JetsnackTheme.colors].
 */
fun debugColors(
    darkTheme: Boolean,
    debugColor: Color = light50
) = Colors(
    primary = debugColor,
    primaryVariant = debugColor,
    secondary = debugColor,
    secondaryVariant = debugColor,
    background = debugColor,
    surface = debugColor,
    error = debugColor,
    onPrimary = debugColor,
    onSecondary = debugColor,
    onBackground = debugColor,
    onSurface = debugColor,
    onError = debugColor,
    isLight = !darkTheme
)
