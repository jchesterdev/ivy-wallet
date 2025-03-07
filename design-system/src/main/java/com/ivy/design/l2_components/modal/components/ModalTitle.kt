package com.ivy.design.l2_components.modal.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ivy.design.l0_system.UI
import com.ivy.design.l1_buildingBlocks.SpacerVer
import com.ivy.design.l2_components.B1
import com.ivy.design.l2_components.modal.IvyModal
import com.ivy.design.l2_components.modal.Modal
import com.ivy.design.l2_components.modal.scope.ModalScope
import com.ivy.design.util.IvyPreview

@Suppress("unused")
@Composable
fun ModalScope.Title(
    text: String,
    color: Color = UI.colorsInverted.pure
) {
    SpacerVer(height = 24.dp)
    text.B1(
        modifier = Modifier.padding(start = 32.dp),
        fontWeight = FontWeight.ExtraBold,
        color = color
    )
}

@Preview
@Composable
private fun Preview() {
    val modal = IvyModal()
    modal.show()
    IvyPreview {
        Modal(modal = modal, Actions = {}) {
            Title(text = "Title")
            SpacerVer(height = 32.dp)
        }
    }
}