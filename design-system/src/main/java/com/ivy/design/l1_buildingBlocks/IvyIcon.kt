package com.ivy.design.l1_buildingBlocks

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ivy.design.l0_system.UI
import com.ivy.design.util.thenWhen

@Composable
fun Int.Icon(
    modifier: Modifier = Modifier,
    tint: Color = UI.colorsInverted.pure,
    contentDescription: String = "icon"
) {
    Icon(
        modifier = modifier,
        painter = painterResource(id = this),
        contentDescription = contentDescription,
        tint = tint
    )
}

@Deprecated("use Int.Icon")
@Composable
fun IvyIcon(
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    tint: Color = UI.colorsInverted.pure,
    contentDescription: String = "icon"
) {
    Icon(
        modifier = modifier,
        painter = painterResource(id = icon),
        contentDescription = contentDescription,
        tint = tint
    )
}

@Deprecated("don't use")
@Composable
fun IvyIconScaled(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    tint: Color = UI.colorsInverted.pure,
    iconScale: IconScale,
    padding: Dp = when (iconScale) {
        IconScale.S -> 4.dp
        IconScale.M -> 4.dp
        IconScale.L -> 4.dp
    },
    contentDescription: String = "icon"
) {
    Image(
        modifier = modifier
            .thenWhen {
                when (iconScale) {
                    IconScale.L ->
                        this.size(64.dp)
                    IconScale.M ->
                        this.size(48.dp)
                    IconScale.S ->
                        this.size(32.dp)
                }
            }
            .padding(all = padding),
        painter = painterResource(id = icon),
        colorFilter = ColorFilter.tint(tint),
        alignment = Alignment.Center,
        contentScale = ContentScale.Fit,
        contentDescription = contentDescription
    )
}

@Deprecated("don't use")
enum class IconScale {
    S, M, L
}