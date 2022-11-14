package com.todayeouido.tayapp.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.todayeouido.tayapp.presentation.states.UserState
import com.todayeouido.tayapp.utils.ThemeConstants.DARK
import com.todayeouido.tayapp.utils.ThemeConstants.LIGHT
import com.google.accompanist.systemuicontroller.rememberSystemUiController


private val DarkColorPalette = TayColorSystem(
    primary = dm_primary50,
    defaultButton = dm_primary50,
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
    textHighlight = dm_sub20,
    danger1 = dm_semantic_red1,
    danger2 = dm_semantic_red2,
    success1 = dm_semantic_green1,
    success2 = dm_semantic_green2,
    caution1 = dm_semantic_yellow1,
    caution2 = dm_semantic_yellow2,
    information1 = dm_semantic_blue1,
    information2 = dm_semantic_blue2,
    gray500 = dm_gray500,
    sub10 = dm_sub10,
    sub20 = dm_sub20,
    sub50 = dm_sub50,
    primary20 = dm_primary20,
    primary30 = dm_primary30,
    isDark = false,
)

private val LightColorPalette = TayColorSystem(
    primary = lm_primary50,
    defaultButton = lm_primary50,
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
    textHighlight = lm_sub20,
    danger1 = lm_semantic_red1,
    danger2 = lm_semantic_red2,
    success1 = lm_semantic_green1,
    success2 = lm_semantic_green2,
    caution1 = lm_semantic_yellow1,
    caution2 = lm_semantic_yellow2,
    information1 = lm_semantic_blue1,
    information2 = lm_semantic_blue2,
    gray500 = lm_gray500,
    sub10 = lm_sub10,
    sub20 = lm_sub20,
    sub50 = lm_sub50,
    primary20 = lm_primary20,
    primary30 = lm_primary30,
    isDark = true,
)

@Composable
fun rememberThemeMode(): Boolean {
    return when (UserState.mode) {
        LIGHT -> false
        DARK -> true
        else -> isSystemInDarkTheme()
    }
}


@Composable
fun TayAppTheme(darkTheme: Boolean = rememberThemeMode(), content: @Composable () -> Unit) {
    val tayColorSystem = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    val tayTypography = TayTypography
    val tayTypographySystem = TayTypographySystem(
        typography = tayTypography
    )

    val sysUiController = rememberSystemUiController()
    SideEffect {
        sysUiController.setSystemBarsColor(
            color = tayColorSystem.background.copy(alpha = AlphaNearOpaque)
        )
    }

    ProvideTaySystem(colors = tayColorSystem, typo = tayTypographySystem) {
        MaterialTheme(
            colors = debugColors(darkTheme),
            shapes = Shapes,
            content = content,
            typography = tayTypography
        )
    }
}

/**
 * object TAYAppTheme 없으면
 * TAYAppTheme.colors. 호출불가
 */
object TayAppTheme {
    val colors: TayColorSystem
        @Composable
        get() = LocalTayColorSystem.current

    val typo: TayTypographySystem
        @Composable
        get() = LocalTayTypographySystem.current

    const val isDark = false
}

/**
 * custom Color Palette
 */

data class TayTypographySystem(
    val typography: Typography
)

val LocalTayTypographySystem = staticCompositionLocalOf {
    TayTypographySystem(
        typography = TayTypography
    )
}

/**
 * custom Color Palette
 */
@Stable
class TayColorSystem(
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
    textHighlight: Color,
    danger1: Color,
    danger2: Color,
    success1: Color,
    success2: Color,
    caution1: Color,
    caution2: Color,
    information1: Color,
    information2: Color,
    gray500: Color,
    sub10: Color,
    sub20: Color,
    sub50: Color,
    primary20: Color,
    primary30: Color,
    isDark: Boolean
) {
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
    var textHighlight by mutableStateOf(textHighlight)
        private set
    var danger1 by mutableStateOf(danger1)
        private set
    var danger2 by mutableStateOf(danger2)
        private set
    var success1 by mutableStateOf(success1)
        private set
    var success2 by mutableStateOf(success2)
        private set
    var caution1 by mutableStateOf(caution1)
        private set
    var caution2 by mutableStateOf(caution2)
        private set
    var information1 by mutableStateOf(information1)
        private set
    var information2 by mutableStateOf(information2)
        private set
    var gray500 by mutableStateOf(gray500)
        private set
    var sub10 by mutableStateOf(sub10)
        private set
    var sub20 by mutableStateOf(sub20)
        private set
    var sub50 by mutableStateOf(sub50)
        private set
    var primary20 by mutableStateOf(primary20)
        private set
    var primary30 by mutableStateOf(primary30)
        private set


    var isDark by mutableStateOf(isDark)
        private set

    /**
     * set(update) function
     */
    fun update(other: TayColorSystem) {
        primary = other.primary
        defaultButton = other.defaultButton
        background = other.background
        layer1 = other.layer1
        layer2 = other.layer2
        layer3 = other.layer3
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
        textHighlight = other.textHighlight
        danger1 = other.danger1
        danger2 = other.danger2
        success1 = other.success1
        success2 = other.success2
        caution1 = other.caution1
        caution2 = other.caution2
        information1 = other.information1
        information2 = other.information2

        gray500 = other.gray500
        sub10 = other.sub10
        sub20 = other.sub20
        sub50 = other.sub50
        primary20 = other.primary20
        primary30 = other.primary30

        isDark = other.isDark
    }

    /**
     * return TAYColors( get)
     */
    fun copy(): TayColorSystem = TayColorSystem(
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
        textHighlight = textHighlight,
        danger1 = danger1,
        danger2 = danger2,
        success1 = success1,
        success2 = success2,
        caution1 = caution1,
        caution2 = caution2,
        information1 = information1,
        information2 = information2,

        gray500 = gray500,
        primary30 = primary30,
        primary20 = primary20,
        sub10 = sub10,
        sub20 = sub20,
        sub50 = sub50,
        isDark = isDark,
    )
}

@Composable
fun ProvideTaySystem(
    colors: TayColorSystem,
    typo: TayTypographySystem,
    content: @Composable () -> Unit
) {
    val colorPalette = remember {
        colors.copy()
    }

    colorPalette.update(colors)
    CompositionLocalProvider(
        LocalTayColorSystem provides colorPalette,
        LocalTayTypographySystem provides typo,
        content = content
    )
}

private val LocalTayColorSystem = staticCompositionLocalOf<TayColorSystem> {
    error("No ColorPalette")
}

/**
 * A Material [Colors] implementation which sets all colors to [debugColor] to discourage usage of
 * [MaterialTheme.colors] in preference to [JetsnackTheme.colors].
 */
fun debugColors(
    darkTheme: Boolean,
    debugColor: Color = lm_primary50
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
