package com.ivy.core.ui.transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ivy.common.timeNowUTC
import com.ivy.core.domain.pure.format.dummyFormattedValue
import com.ivy.core.ui.data.AccountUi
import com.ivy.core.ui.data.CategoryUi
import com.ivy.core.ui.data.dummyAccountUi
import com.ivy.core.ui.data.dummyCategoryUi
import com.ivy.core.ui.data.icon.dummyIconSized
import com.ivy.core.ui.data.icon.dummyIconUnknown
import com.ivy.core.ui.data.transaction.*
import com.ivy.core.ui.transaction.card.Card
import com.ivy.core.ui.transaction.card.DueActions
import com.ivy.core.ui.transaction.card.dummyDueActions
import com.ivy.data.transaction.TrnType
import com.ivy.design.l0_system.UI
import com.ivy.design.l0_system.color.*
import com.ivy.design.l1_buildingBlocks.Icon
import com.ivy.design.l1_buildingBlocks.SpacerVer
import com.ivy.design.l2_components.B1
import com.ivy.design.l2_components.B2
import com.ivy.design.util.IvyPreview
import com.ivy.resources.R

// region Expand & Collapse Handling
@Immutable
data class ExpandCollapseHandler(
    val expanded: Boolean,
    val setExpanded: (Boolean) -> Unit,
)

@Composable
fun defaultExpandCollapseHandler(): ExpandCollapseHandler {
    var expanded by remember { mutableStateOf(false) }
    return ExpandCollapseHandler(
        expanded = expanded,
        setExpanded = { expanded = it }
    )
}
// endregion

// region Transaction Item Click Handling
@Immutable
data class TrnItemClickHandler(
    val onTrnClick: (TransactionUi) -> Unit,
    val onTransferClick: (TrnListItemUi.Transfer) -> Unit,
    val onAccountClick: (AccountUi) -> Unit,
    val onCategoryClick: (CategoryUi) -> Unit,
)

fun dummyTrnItemClickHandler(): TrnItemClickHandler = TrnItemClickHandler(
    onTrnClick = {},
    onTransferClick = {},
    onCategoryClick = {},
    onAccountClick = {},
)
// endregion

// region EmptyState data
@Immutable
data class EmptyState(
    val title: String,
    val description: String,
)

@Composable
fun defaultEmptyState() = EmptyState(
    title = stringResource(R.string.no_transactions),
    description = stringResource(R.string.no_transactions_desc)
)
// endregion

internal fun LazyListScope.transactionsList(
    trnsList: TransactionsListUi,
    emptyState: EmptyState,

    upcomingHandler: ExpandCollapseHandler,
    overdueHandler: ExpandCollapseHandler,
    dueActions: DueActions?,
    trnClickHandler: TrnItemClickHandler
) {
    dueSection(
        section = trnsList.upcoming,
        handler = upcomingHandler,
        trnClickHandler = trnClickHandler,
        dueActions = dueActions
    )
    dueSection(
        section = trnsList.overdue,
        handler = overdueHandler,
        trnClickHandler = trnClickHandler,
        dueActions = dueActions
    )
    history(history = trnsList.history, trnClickHandler = trnClickHandler)

    val isEmpty by derivedStateOf {
        trnsList.history.isEmpty() && trnsList.upcoming == null && trnsList.overdue == null
    }
    if (isEmpty) {
        emptyState(emptyState)
    }
}


private fun LazyListScope.dueSection(
    section: DueSectionUi?,
    handler: ExpandCollapseHandler,
    trnClickHandler: TrnItemClickHandler,
    dueActions: DueActions?,
) {
    if (section != null) {
        item {
            SpacerVer(height = 24.dp)
            section.SectionDivider(
                expanded = handler.expanded,
                setExpanded = handler.setExpanded,
            )
        }
        if (handler.expanded) {
            dueTrns(
                trns = section.trns,
                dueActions = dueActions,
                trnClickHandler = trnClickHandler
            )
        }
    }
}

private fun LazyListScope.dueTrns(
    trns: List<TransactionUi>,
    trnClickHandler: TrnItemClickHandler,
    dueActions: DueActions?,
) {
    items(
        items = trns,
        key = { it.id }
    ) { trn ->
        SpacerVer(height = 12.dp)
        trn.Card(
            modifier = Modifier.padding(horizontal = 16.dp),
            onClick = trnClickHandler.onTrnClick,
            onAccountClick = trnClickHandler.onAccountClick,
            onCategoryClick = trnClickHandler.onCategoryClick,
            dueActions = dueActions
        )
    }
}

private fun LazyListScope.history(
    history: List<TrnListItemUi>,
    trnClickHandler: TrnItemClickHandler
) {
    itemsIndexed(
        items = history,
        key = { _, item ->
            when (item) {
                is TrnListItemUi.DateDivider -> item.date
                is TrnListItemUi.Trn -> item.trn.id
                is TrnListItemUi.Transfer -> item.batchId
            }
        }
    ) { index, item ->
        when (item) {
            is TrnListItemUi.DateDivider -> {
                SpacerVer(
                    // the first date divider require less margin
                    height = if (index > 0 && history[index - 1] !is TrnListItemUi.DateDivider)
                        32.dp else 24.dp
                )
                item.DateDivider()
            }
            is TrnListItemUi.Trn -> {
                SpacerVer(height = 12.dp)
                item.trn.Card(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onClick = trnClickHandler.onTrnClick,
                    onAccountClick = trnClickHandler.onAccountClick,
                    onCategoryClick = trnClickHandler.onCategoryClick,
                )
            }
            is TrnListItemUi.Transfer -> {
                SpacerVer(height = 12.dp)
                item.Card(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onClick = trnClickHandler.onTransferClick
                )
            }
        }
    }
}

private fun LazyListScope.emptyState(emptyState: EmptyState) {
    item {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .padding(vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            R.drawable.ic_notransactions.Icon(tint = UI.colors.neutral)
            SpacerVer(height = 24.dp)
            emptyState.title.B1(
                modifier = Modifier.fillMaxWidth(),
                color = UI.colors.neutral,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
            )
            SpacerVer(height = 8.dp)
            emptyState.description.B2(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                color = UI.colors.neutral,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    }
}

// region Previews
@Preview
@Composable
private fun Preview_Full() {
    IvyPreview {
        val upcomingHandler = defaultExpandCollapseHandler()
        val overdueHandler = defaultExpandCollapseHandler()
        val emptyState = defaultEmptyState()
        val trnClickHandler = dummyTrnItemClickHandler()

        val trnsList = TransactionsListUi(
            upcoming = DueSectionUi(
                dueType = DueSectionUiType.Upcoming,
                income = dummyFormattedValue("16.99"),
                expense = null,
                trns = listOf(
                    dummyTransactionUi(
                        title = "Upcoming payment",
                        account = dummyAccountUi(
                            name = "Revolut",
                            color = Purple,
                            icon = dummyIconSized(R.drawable.ic_custom_revolut_s)
                        ),
                        category = dummyCategoryUi(
                            name = "Investments",
                            color = Blue2Light,
                            icon = dummyIconSized(R.drawable.ic_custom_leaf_s)
                        ),
                        value = dummyFormattedValue("16.99"),
                        type = TrnType.Income,
                        time = dummyTrnTimeDueUi(timeNowUTC().plusDays(1))
                    )
                )
            ),
            overdue = DueSectionUi(
                dueType = DueSectionUiType.Overdue,
                income = null,
                expense = dummyFormattedValue("650.0"),
                trns = listOf(
                    dummyTransactionUi(
                        title = "Rent",
                        value = dummyFormattedValue("650.0"),
                        account = dummyAccountUi(
                            name = "Cash",
                            color = Green,
                            icon = dummyIconUnknown(R.drawable.ic_vue_money_coins)
                        ),
                        category = null,
                        type = TrnType.Expense,
                        time = dummyTrnTimeDueUi()
                    )
                )
            ),
            history = listOf(
                TrnListItemUi.DateDivider(
                    date = "September 25.",
                    day = "Friday",
                    cashflow = dummyFormattedValue("-30.0"),
                    positiveCashflow = false
                ),
                TrnListItemUi.Trn(
                    dummyTransactionUi(
                        title = "Food",
                        account = dummyAccountUi(
                            name = "Revolut",
                            color = Purple,
                            icon = dummyIconSized(R.drawable.ic_custom_revolut_s)
                        ),
                        category = dummyCategoryUi(
                            name = "Order food",
                            color = Orange2,
                            icon = dummyIconSized(R.drawable.ic_custom_orderfood_s)
                        ),
                        value = dummyFormattedValue("30.0"),
                        type = TrnType.Expense,
                        time = dummyTrnTimeActualUi()
                    )
                ),
                TrnListItemUi.DateDivider(
                    date = "September 23.",
                    day = "Wednesday",
                    cashflow = dummyFormattedValue("105.33"),
                    positiveCashflow = true
                ),
                TrnListItemUi.Trn(
                    dummyTransactionUi(
                        title = "Buy some cool gadgets",
                        description = "Premium tech!",
                        account = dummyAccountUi(
                            name = "Bank",
                            color = Red,
                            icon = dummyIconSized(R.drawable.ic_custom_bank_s)
                        ),
                        category = dummyCategoryUi(
                            name = "Tech",
                            color = Blue2Dark,
                            icon = dummyIconUnknown(R.drawable.ic_vue_edu_telescope)
                        ),
                        value = dummyFormattedValue("55.23"),
                        type = TrnType.Expense,
                    )
                ),
                TrnListItemUi.Trn(
                    dummyTransactionUi(
                        title = "Ivy Apps revenue",
                        account = dummyAccountUi(
                            name = "Revolut Business",
                            color = Purple2Dark,
                            icon = dummyIconSized(R.drawable.ic_custom_revolut_s)
                        ),
                        category = null,
                        value = dummyFormattedValue("160.53"),
                        type = TrnType.Income,
                    )
                ),
                TrnListItemUi.Trn(
                    dummyTransactionUi(
                        title = "Buy some cool gadgets",
                        description = "Premium tech!",
                        account = dummyAccountUi(
                            name = "Bank",
                            color = Red,
                            icon = dummyIconSized(R.drawable.ic_custom_bank_s)
                        ),
                        category = dummyCategoryUi(
                            name = "Tech",
                            color = Blue2Dark,
                            icon = dummyIconUnknown(R.drawable.ic_vue_edu_telescope)
                        ),
                        value = dummyFormattedValue("55.23"),
                        type = TrnType.Expense,
                    )
                ),
            )
        )

        LazyColumn {
            transactionsList(
                trnsList = trnsList,
                emptyState = emptyState,
                upcomingHandler = upcomingHandler,
                overdueHandler = overdueHandler,
                dueActions = dummyDueActions(),
                trnClickHandler = trnClickHandler,
            )
        }
    }
}

@Preview
@Composable
private fun Preview_EmptyState() {
    IvyPreview {
        val upcomingHandler = defaultExpandCollapseHandler()
        val overdueHandler = defaultExpandCollapseHandler()
        val emptyState = defaultEmptyState()
        val trnClickHandler = dummyTrnItemClickHandler()

        LazyColumn {
            val trnsList = TransactionsListUi(
                upcoming = null,
                overdue = null,
                history = emptyList()
            )

            transactionsList(
                trnsList = trnsList,
                emptyState = emptyState,
                upcomingHandler = upcomingHandler,
                overdueHandler = overdueHandler,
                dueActions = dummyDueActions(),
                trnClickHandler = trnClickHandler,
            )
        }
    }
}
// endregion