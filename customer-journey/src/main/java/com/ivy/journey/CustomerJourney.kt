package com.ivy.journey

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ivy.design.l0_system.UI
import com.ivy.design.l0_system.color.contrastColor
import com.ivy.design.l0_system.style
import com.ivy.design.util.ComponentPreview
import com.ivy.frp.view.navigation.navigation
import com.ivy.journey.domain.CustomerJourneyCardData
import com.ivy.journey.domain.CustomerJourneyLogic
import com.ivy.wallet.ui.theme.Gradient
import com.ivy.wallet.ui.theme.components.IvyButton
import com.ivy.wallet.ui.theme.components.IvyIcon
import com.ivy.wallet.ui.theme.dynamicContrast
import com.ivy.wallet.utils.drawColoredShadow

@Composable
fun CustomerJourney(
    customerJourneyCards: List<CustomerJourneyCardData>,
    onDismiss: (CustomerJourneyCardData) -> Unit
) {
    val nav = navigation()
    val rootScreen = com.ivy.core.ui.temp.rootScreen()

    if (customerJourneyCards.isNotEmpty()) {
        Spacer(Modifier.height(12.dp))
    }

    for (card in customerJourneyCards) {
        Spacer(Modifier.height(12.dp))

        CustomerJourneyCard(
            cardData = card,
            onDismiss = {
                onDismiss(card)
            }
        ) {
//            card.onAction(nav, ivyContext, rootScreen)
        }
    }
}

@Composable
fun CustomerJourneyCard(
    cardData: CustomerJourneyCardData,

    onDismiss: () -> Unit,
    onCTA: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .drawColoredShadow(cardData.background.start)
            .background(cardData.background.asHorizontalBrush(), UI.shapes.rounded)
            .clip(UI.shapes.rounded)
            .clickable {
                onCTA()
            }
    ) {
        Spacer(Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 24.dp, end = 16.dp),
                text = cardData.title,
                style = UI.typo.b1.style(
                    fontWeight = FontWeight.ExtraBold,
                    color = contrastColor(cardData.background.start)
                )
            )

            if (cardData.hasDismiss) {
                IvyIcon(
                    modifier = Modifier
                        .clickable {
                            onDismiss()
                        }
                        .padding(8.dp), //enlarge click area
                    icon = R.drawable.ic_dismiss,
                    tint = cardData.background.start.dynamicContrast(),
                    contentDescription = "prompt_dismiss",
                )

                Spacer(Modifier.width(20.dp))
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 32.dp),
            text = cardData.description,
            style = UI.typo.b2.style(
                fontWeight = FontWeight.Medium,
                color = contrastColor(cardData.background.start)
            )
        )

        Spacer(Modifier.height(32.dp))

        IvyButton(
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 20.dp)
                .testTag("cta_prompt_${cardData.id}"),
            text = cardData.cta,
            shadowAlpha = 0f,
            iconStart = cardData.ctaIcon,
            iconTint = cardData.background.start,
            textStyle = UI.typo.b2.style(
                color = cardData.background.start,
                fontWeight = FontWeight.Bold
            ),
            padding = 8.dp,
            backgroundGradient = Gradient.solid(contrastColor(cardData.background.start))
        ) {
            onCTA()
        }

        Spacer(Modifier.height(20.dp))
    }
}

@Preview
@Composable
private fun PreviewCard() {
    ComponentPreview {
        CustomerJourneyCard(
            cardData = CustomerJourneyLogic.adjustBalanceCard(),
            onCTA = { },
            onDismiss = {}
        )
    }
}