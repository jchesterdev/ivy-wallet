package com.ivy.transaction_details


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ivy.base.R
import com.ivy.design.l0_system.UI
import com.ivy.design.l0_system.style
import com.ivy.design.util.ComponentPreview
import com.ivy.wallet.ui.theme.components.IvyIcon
import com.ivy.wallet.utils.formatLocalTime
import com.ivy.wallet.utils.formatNicely
import com.ivy.wallet.utils.timeNowUTC
import java.time.LocalDateTime

@Composable
fun TransactionDateTime(
    dateTime: LocalDateTime?,
    dueDateTime: LocalDateTime?,

    onEditDate: () -> Unit = {},
    onEditTime: () -> Unit = {},
    onEditDateTime: () -> Unit
) {
    if (dueDateTime == null || dateTime != null) {
        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .clip(UI.shapes.squared)
                .background(UI.colors.medium, UI.shapes.squared)
                .clickable {
                    onEditDateTime()
                }
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(16.dp))

            IvyIcon(icon = R.drawable.ic_calendar)

            Spacer(Modifier.width(8.dp))

            Text(
                text = stringResource(R.string.created_on),
                style = UI.typo.b2.style(
                    color = UI.colors.neutral,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(Modifier.width(24.dp))
            Spacer(Modifier.weight(1f))

            Text(
                text = (dateTime ?: timeNowUTC()).formatNicely(
                    noWeekDay = true
                ),
                style = UI.typoSecond.b2.style(
                    color = UI.colorsInverted.pure,
                    fontWeight = FontWeight.ExtraBold
                ),
                modifier = Modifier.clickable {
                    onEditDate()
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = (dateTime ?: timeNowUTC()).formatLocalTime(),
                style = UI.typoSecond.b2.style(
                    color = UI.colorsInverted.pure,
                    fontWeight = FontWeight.ExtraBold
                ),
                modifier = Modifier.clickable {
                    onEditTime()
                }
            )

            Spacer(modifier = Modifier.width(24.dp))
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ComponentPreview {
        TransactionDateTime(
            dateTime = timeNowUTC(),
            dueDateTime = null
        ) {

        }
    }
}