package com.ivy.core.domain.pure.dummy

import com.ivy.common.timeNowLocal
import com.ivy.data.CurrencyCode
import com.ivy.data.SyncState
import com.ivy.data.Value
import com.ivy.data.account.Account
import com.ivy.data.attachment.Attachment
import com.ivy.data.category.Category
import com.ivy.data.tag.Tag
import com.ivy.data.transaction.*
import java.time.LocalDateTime
import java.util.*

fun dummyTrn(
    id: UUID = UUID.randomUUID(),
    account: Account = dummyAcc(),
    type: TrnType = TrnType.Income,
    amount: Double = 0.0,
    currency: CurrencyCode? = null,
    category: Category? = dummyCategory(),
    time: TrnTime = TrnTime.Actual(timeNowLocal()),
    title: String? = "Dummy trn",
    description: String? = null,
    tags: List<Tag> = emptyList(),
    attachments: List<Attachment> = emptyList(),
    metadata: TrnMetadata = dummyTrnMetadata(),
    state: TrnState = TrnState.Default,
    purpose: TrnPurpose? = null,
    sync: SyncState = SyncState.Synced,
): Transaction = Transaction(
    id = id,
    account = account,
    type = type,
    value = Value(
        amount = amount,
        currency = currency ?: account.currency,
    ),
    category = category,
    time = time,
    title = title,
    description = description,
    metadata = metadata,
    state = state,
    purpose = purpose,
    sync = sync,
    tags = tags,
    attachments = attachments,
)

fun dummyValue(
    amount: Double = 0.0,
    currency: CurrencyCode = "USD"
): Value = Value(
    amount = amount,
    currency = currency,
)


fun dummyActual(
    time: LocalDateTime = timeNowLocal()
): TrnTime.Actual = TrnTime.Actual(time)


fun dummyDue(
    time: LocalDateTime = timeNowLocal()
): TrnTime.Due = TrnTime.Due(time)

fun dummyTrnMetadata(
    recurringRuleId: UUID? = null,
    loanId: UUID? = null,
    loanRecordId: UUID? = null,
) = TrnMetadata(
    recurringRuleId = recurringRuleId,
    loanId = loanId,
    loanRecordId = loanRecordId
)