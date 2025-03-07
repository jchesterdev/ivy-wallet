package com.ivy.core.ui.icon

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ivy.core.ui.R
import com.ivy.core.ui.data.icon.IconSize
import com.ivy.core.ui.data.icon.IvyIcon
import com.ivy.design.l0_system.UI
import com.ivy.design.util.ComponentPreview

@Composable
fun IvyIcon.ItemIcon(
    size: IconSize,
    tint: Color = UI.colorsInverted.pure
) {
    when (this) {
        is IvyIcon.Sized -> when (size) {
            com.ivy.core.ui.data.icon.IconSize.S -> Icon(
                modifier = Modifier
                    .size(size.toDp()),
                icon = iconS,
                tint = tint,
            )
            com.ivy.core.ui.data.icon.IconSize.M -> Icon(
                modifier = Modifier
                    .size(size.toDp()),
                icon = iconM,
                tint = tint,
            )
            com.ivy.core.ui.data.icon.IconSize.L -> Icon(
                modifier = Modifier
                    .size(size.toDp()),
                icon = iconL,
                tint = tint,
            )
        }
        is IvyIcon.Unknown -> Image(
            modifier = Modifier
                .size(size.toDp())
                .padding(all = 4.dp),
            painter = painterResource(icon),
            contentDescription = "itemIcon",
            colorFilter = ColorFilter.tint(tint),
            alignment = Alignment.Center,
            contentScale = ContentScale.FillBounds,
        )
    }
}

@Composable
private fun IconSize.toDp(): Dp = remember(this) {
    when (this) {
        IconSize.S -> 32.dp
        IconSize.M -> 48.dp
        IconSize.L -> 64.dp
    }
}

@Composable
private fun Icon(
    modifier: Modifier = Modifier,
    @DrawableRes
    icon: Int,
    tint: Color,
) {
    androidx.compose.material.Icon(
        modifier = modifier,
        painter = painterResource(id = icon),
        contentDescription = "itemIcon",
        tint = tint
    )
}

//region Previews
@Preview
@Composable
private fun Preview_Sized_S() {
    ComponentPreview {
        IvyIcon.Sized(
            iconS = R.drawable.ic_custom_account_s,
            iconM = 0,
            iconL = 0,
            iconId = null
        ).ItemIcon(size = IconSize.S)
    }
}

@Preview
@Composable
private fun Preview_Sized_M() {
    ComponentPreview {
        IvyIcon.Sized(
            iconS = 0,
            iconM = R.drawable.ic_custom_account_m,
            iconL = 0,
            iconId = null
        ).ItemIcon(size = IconSize.M)
    }
}

@Preview
@Composable
private fun Preview_Sized_L() {
    ComponentPreview {
        IvyIcon.Sized(
            iconS = 0,
            iconM = 0,
            iconL = R.drawable.ic_custom_account_l,
            iconId = null
        ).ItemIcon(size = IconSize.L)
    }
}

@Preview
@Composable
private fun Preview_Unknown_S() {
    ComponentPreview {
        IvyIcon.Unknown(
            icon = R.drawable.ic_vue_crypto_cardano,
            iconId = null
        ).ItemIcon(size = IconSize.S)
    }
}

@Preview
@Composable
private fun Preview_Unknown_M() {
    ComponentPreview {
        IvyIcon.Unknown(
            icon = R.drawable.ic_vue_crypto_cardano,
            iconId = null
        ).ItemIcon(size = IconSize.M)
    }
}

@Preview
@Composable
private fun Preview_Unknown_L() {
    ComponentPreview {
        IvyIcon.Unknown(
            icon = R.drawable.ic_vue_crypto_cardano,
            iconId = null
        ).ItemIcon(size = IconSize.L)
    }
}
//endregion

