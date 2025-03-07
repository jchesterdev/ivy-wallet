package com.ivy.wallet.ui.planned.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ivy.base.R
import com.ivy.core.ui.temp.trash.forDisplay
import com.ivy.data.AccountOld
import com.ivy.data.CategoryOld
import com.ivy.data.planned.IntervalType
import com.ivy.data.planned.PlannedPaymentRule
import com.ivy.data.transaction.TrnTypeOld
import com.ivy.design.l0_system.UI
import com.ivy.design.l0_system.style
import com.ivy.design.util.IvyPreview
import com.ivy.frp.view.navigation.navigation
import com.ivy.screens.ItemStatistic
import com.ivy.wallet.ui.component.transaction.TypeAmountCurrency
import com.ivy.wallet.ui.theme.*
import com.ivy.wallet.ui.theme.components.IvyButton
import com.ivy.wallet.ui.theme.components.IvyIcon
import com.ivy.wallet.ui.theme.components.getCustomIconIdS
import com.ivy.wallet.utils.*
import java.time.LocalDateTime

@Composable
fun LazyItemScope.PlannedPaymentCard(
    baseCurrency: String,
    categories: List<CategoryOld>,
    accounts: List<AccountOld>,
    plannedPayment: PlannedPaymentRule,
    onClick: (PlannedPaymentRule) -> Unit,
) {
    Spacer(Modifier.height(12.dp))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(UI.shapes.squared)
            .clickable {
                if (accounts.find { it.id == plannedPayment.accountId } != null) {
                    onClick(plannedPayment)
                }
            }
            .background(UI.colors.medium, UI.shapes.squared)
            .testTag("planned_payment_card")
    ) {
        val currency = accounts.find { it.id == plannedPayment.accountId }?.currency ?: baseCurrency

        Spacer(Modifier.height(20.dp))

        PlannedPaymentHeaderRow(
            plannedPayment = plannedPayment,
            categories = categories,
            accounts = accounts
        )

        Spacer(Modifier.height(16.dp))

        RuleTextRow(
            oneTime = plannedPayment.oneTime,
            startDate = plannedPayment.startDate,
            intervalN = plannedPayment.intervalN,
            intervalType = plannedPayment.intervalType
        )

        if (plannedPayment.title.isNotNullOrBlank()) {
            Spacer(Modifier.height(8.dp))

            Text(
                modifier = Modifier.padding(horizontal = 24.dp),
                text = plannedPayment.title!!,
                style = UI.typo.b1.style(
                    fontWeight = FontWeight.ExtraBold,
                    color = UI.colorsInverted.pure
                )
            )
        }

        Spacer(Modifier.height(20.dp))

        TypeAmountCurrency(
            transactionType = plannedPayment.type,
            dueDate = null,
            currency = currency,
            amount = plannedPayment.amount
        )

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun PlannedPaymentHeaderRow(
    plannedPayment: PlannedPaymentRule,
    categories: List<CategoryOld>,
    accounts: List<AccountOld>
) {
    val nav = navigation()

    if (plannedPayment.type != TrnTypeOld.TRANSFER) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(20.dp))

            IvyIcon(
                modifier = Modifier
                    .background(UI.colors.pure, CircleShape),
                icon = R.drawable.ic_planned_payments,
                tint = UI.colorsInverted.pure
            )

            Spacer(Modifier.width(12.dp))

            val category =
                plannedPayment.categoryId?.let { targetId -> categories.find { it.id == targetId } }
            if (category != null) {
                IvyButton(
                    iconTint = findContrastTextColor(category.color.toComposeColor()),
                    iconStart = getCustomIconIdS(category.icon, R.drawable.ic_custom_category_s),
                    text = category.name,
                    backgroundGradient = Gradient.solid(category.color.toComposeColor()),
                    textStyle = UI.typo.c.style(
                        color = findContrastTextColor(category.color.toComposeColor()),
                        fontWeight = FontWeight.ExtraBold
                    ),
                    padding = 8.dp,
                    iconEdgePadding = 10.dp
                ) {
                    nav.navigateTo(
                        ItemStatistic(
                            accountId = null,
                            categoryId = category.id
                        )
                    )
                }

                Spacer(Modifier.width(12.dp))
            }

            val account = accounts.find { it.id == plannedPayment.accountId }
            IvyButton(
                backgroundGradient = Gradient.solid(UI.colors.pure),
                text = account?.name ?: stringResource(R.string.deleted),
                iconTint = UI.colorsInverted.pure,
                iconStart = getCustomIconIdS(account?.icon, R.drawable.ic_custom_account_s),
                textStyle = UI.typo.c.style(
                    color = UI.colorsInverted.pure,
                    fontWeight = FontWeight.ExtraBold
                ),
                padding = 8.dp,
                iconEdgePadding = 10.dp
            ) {
                account?.let {
                    nav.navigateTo(
                        ItemStatistic(
                            accountId = account.id,
                            categoryId = null
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun RuleTextRow(
    oneTime: Boolean,
    startDate: LocalDateTime?,
    intervalN: Int?,
    intervalType: IntervalType?
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.width(24.dp))

        if (oneTime) {
            Text(
                text = stringResource(R.string.planned_for_uppercase),
                style = UI.typoSecond.c.style(
                    color = Orange,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Text(
                modifier = Modifier.padding(bottom = 1.dp),
                text = startDate?.toLocalDate()?.formatDateOnlyWithYear()?.uppercaseLocal()
                    ?: stringResource(R.string.null_text),
                style = UI.typoSecond.c.style(
                    color = Orange,
                    fontWeight = FontWeight.ExtraBold
                )
            )
        } else {
            val startDateFormatted = startDate?.toLocalDate()?.formatDateOnly()?.uppercaseLocal()
            Text(
                text = stringResource(R.string.starts_date, startDateFormatted ?: ""),
                style = UI.typoSecond.c.style(
                    color = Orange,
                    fontWeight = FontWeight.SemiBold
                )
            )
            val intervalTypeFormatted = intervalType?.forDisplay(intervalN ?: 0)?.uppercaseLocal()
            Text(
                modifier = Modifier.padding(bottom = 1.dp),
                text = stringResource(
                    R.string.repeats_every,
                    intervalN ?: 0,
                    intervalTypeFormatted ?: ""
                ),
                style = UI.typoSecond.c.style(
                    color = Orange,
                    fontWeight = FontWeight.ExtraBold
                )
            )
        }

        Spacer(Modifier.width(24.dp))

    }
}

@Preview
@Composable
private fun Preview_oneTime() {
    IvyPreview {
        LazyColumn(Modifier.fillMaxSize()) {
            val cash = AccountOld(name = "Cash", color = Green.toArgb())
            val food = CategoryOld(name = "Food", color = Green.toArgb())

            item {
                Spacer(Modifier.height(68.dp))

                PlannedPaymentCard(
                    baseCurrency = "BGN",
                    categories = listOf(food),
                    accounts = listOf(cash),
                    plannedPayment = PlannedPaymentRule(
                        accountId = cash.id,
                        title = "Lidl pazar",
                        categoryId = food.id,
                        amount = 250.75,
                        startDate = timeNowUTC().plusDays(5),
                        oneTime = true,
                        intervalType = null,
                        intervalN = null,
                        type = TrnTypeOld.EXPENSE
                    )
                ) {

                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview_recurring() {
    IvyPreview {
        LazyColumn(Modifier.fillMaxSize()) {
            val account = AccountOld(name = "Revolut", color = Green.toArgb())
            val shisha = CategoryOld(name = "Shisha", color = Orange.toArgb())

            item {
                Spacer(Modifier.height(68.dp))

                PlannedPaymentCard(
                    baseCurrency = "BGN",
                    categories = listOf(shisha),
                    accounts = listOf(account),
                    plannedPayment = PlannedPaymentRule(
                        accountId = account.id,
                        title = "Tabu",
                        categoryId = shisha.id,
                        amount = 250.75,
                        startDate = timeNowUTC().plusDays(5),
                        oneTime = false,
                        intervalType = IntervalType.MONTH,
                        intervalN = 1,
                        type = TrnTypeOld.EXPENSE
                    )
                ) {

                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview_recurringError() {
    IvyPreview {
        LazyColumn(Modifier.fillMaxSize()) {
            val account = AccountOld(name = "Revolut", color = Green.toArgb())
            val shisha = CategoryOld(name = "Shisha", color = Orange.toArgb())

            item {
                Spacer(Modifier.height(68.dp))

                PlannedPaymentCard(
                    baseCurrency = "BGN",
                    categories = listOf(shisha),
                    accounts = listOf(account),
                    plannedPayment = PlannedPaymentRule(
                        accountId = account.id,
                        title = "Tabu",
                        categoryId = shisha.id,
                        amount = 250.75,
                        startDate = timeNowUTC().plusDays(5),
                        oneTime = false,
                        intervalType = null,
                        intervalN = null,
                        type = TrnTypeOld.EXPENSE
                    )
                ) {

                }
            }
        }
    }
}
