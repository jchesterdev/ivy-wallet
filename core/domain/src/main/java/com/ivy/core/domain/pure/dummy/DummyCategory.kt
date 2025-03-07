package com.ivy.core.domain.pure.dummy

import androidx.annotation.ColorInt
import com.ivy.data.IvyIconId
import com.ivy.data.SyncState
import com.ivy.data.category.Category
import com.ivy.data.category.CategoryState
import com.ivy.data.category.CategoryType
import java.util.*

fun dummyCategory(
    id: UUID = UUID.randomUUID(),
    name: String = "Dummy Category",
    parentCategoryId: UUID? = null,
    @ColorInt
    color: Int = 1,
    icon: IvyIconId = "category",
    sync: SyncState = SyncState.Synced,
    orderNum: Double = 0.0,
    type: CategoryType = CategoryType.Both,
    state: CategoryState = CategoryState.Default,
): Category = Category(
    id = id,
    name = name,
    parentCategoryId = parentCategoryId,
    color = color,
    icon = icon,
    sync = sync,
    orderNum = orderNum,
    type = type,
    state = state,
)
