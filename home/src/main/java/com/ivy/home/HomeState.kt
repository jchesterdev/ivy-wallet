package com.ivy.home

import com.ivy.data.Value
import com.ivy.data.time.SelectedPeriod
import com.ivy.data.transaction.TransactionsList

data class HomeState(
    val name: String,
    val period: SelectedPeriod?,
    val trnsList: TransactionsList,
    val balance: Value,
    val income: Value,
    val expense: Value,
    val hideBalance: Boolean
)