package com.ivy.wallet.ui.loan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ivy.base.R
import com.ivy.design.util.IvyPreview
import com.ivy.wallet.ui.theme.Blue
import com.ivy.wallet.ui.theme.components.BackBottomBar
import com.ivy.wallet.ui.theme.components.IvyButton

@Composable
internal fun BoxWithConstraintsScope.LoanBottomBar(
    onClose: () -> Unit,
    onAdd: () -> Unit
) {
    BackBottomBar(onBack = onClose) {
        IvyButton(
            text = stringResource(R.string.add_loan),
            iconStart = R.drawable.ic_plus
        ) {
            onAdd()
        }
    }
}

@Preview
@Composable
private fun PreviewBottomBar() {
    IvyPreview {
        Column(
            Modifier
                .fillMaxSize()
                .background(Blue)
        ) {

        }

        LoanBottomBar(
            onAdd = {},
            onClose = {}
        )
    }
}