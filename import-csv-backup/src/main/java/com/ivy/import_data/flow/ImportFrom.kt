package com.ivy.import_data.flow


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ivy.base.R
import com.ivy.design.l0_system.UI
import com.ivy.design.l0_system.style
import com.ivy.design.util.IvyPreview
import com.ivy.frp.view.navigation.navigation
import com.ivy.old.OnboardingToolbar
import com.ivy.wallet.domain.deprecated.logic.csv.model.ImportApp
import com.ivy.wallet.ui.theme.components.GradientCutBottom
import com.ivy.wallet.ui.theme.components.IvyIcon

@ExperimentalFoundationApi
@Composable
fun BoxWithConstraintsScope.ImportFrom(
    hasSkip: Boolean,

    onSkip: () -> Unit = {},
    onImportFrom: (ImportApp) -> Unit = {},
) {
    val importApps = ImportApp.values()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        stickyHeader {
            val nav = navigation()
            OnboardingToolbar(
                hasSkip = hasSkip,
                onBack = { nav.onBackPressed() },
                onSkip = onSkip
            )
            //onboarding toolbar include paddingBottom 16.dp
        }

        item {
            Spacer(Modifier.height(8.dp))

            Text(
                modifier = Modifier.padding(start = 32.dp),
                text = stringResource(R.string.import_from),
                style = UI.typo.h2.style(
                    fontWeight = FontWeight.Black
                )
            )

            Spacer(Modifier.height(24.dp))
        }

        items(importApps) {
            ImportOption(
                importApp = it,
                onImportFrom = onImportFrom
            )
        }

        item {
            //last spacer
            Spacer(Modifier.height(96.dp))
        }
    }

    GradientCutBottom(
        height = 96.dp
    )
}

@Composable
private fun ImportOption(
    importApp: ImportApp,
    onImportFrom: (ImportApp) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(UI.shapes.rounded)
            .background(UI.colors.medium, UI.shapes.rounded)
            .clickable {
                onImportFrom(importApp)
            }
            .padding(vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.width(20.dp))

        IvyIcon(
            modifier = Modifier.size(32.dp),
            icon = importApp.logo(),
            tint = Color.Unspecified
        )

        Text(
            modifier = Modifier.padding(start = 16.dp, end = 32.dp),
            text = importApp.listName(),
            style = UI.typo.b2.style(
                fontWeight = FontWeight.Bold,
                color = UI.colorsInverted.pure
            )
        )
    }

    Spacer(Modifier.height(8.dp))
}

@ExperimentalFoundationApi
@Preview
@Composable
private fun Preview() {
    IvyPreview {
        ImportFrom(
            hasSkip = true,
        )
    }
}