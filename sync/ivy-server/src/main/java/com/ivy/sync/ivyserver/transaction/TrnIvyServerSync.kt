package com.ivy.sync.ivyserver.transaction

import com.ivy.data.transaction.Transaction
import com.ivy.frp.asParamTo
import com.ivy.frp.monad.Res
import com.ivy.frp.monad.mapSuccess
import com.ivy.frp.monad.tryOp
import com.ivy.frp.thenInvokeAfter
import com.ivy.sync.base.SyncItem
import com.ivy.wallet.io.network.IvySession
import com.ivy.wallet.io.network.data.TransactionDTO
import com.ivy.wallet.io.network.request.transaction.DeleteTransactionRequest
import com.ivy.wallet.io.network.request.transaction.UpdateTransactionRequest
import com.ivy.wallet.io.network.service.TransactionService
import javax.inject.Inject

class TrnIvyServerSync @Inject constructor(
    private val ivySession: IvySession,
    private val transactionService: TransactionService
) : SyncItem<Transaction> {
    override suspend fun enabled(): SyncItem<Transaction>? = this.takeIf { ivySession.isLoggedIn() }

    override suspend fun save(items: List<Transaction>): List<Transaction> =
        items.map { saveItem(it) }
            .mapNotNull { if (it is Res.Ok) it.data else null }

    private suspend fun saveItem(item: Transaction): Res<Exception, Transaction> = tryOp(
        operation = UpdateTransactionRequest(
            transaction = mapToDTO(item)
        ) asParamTo transactionService::update
    ) mapSuccess { item } thenInvokeAfter { it }

    override suspend fun delete(items: List<Transaction>): List<Transaction> =
        items.map { deleteItem(it) }
            .mapNotNull { if (it is Res.Ok) it.data else null }

    private suspend fun deleteItem(item: Transaction): Res<Exception, Transaction> = tryOp(
        operation = DeleteTransactionRequest(
            id = item.id
        ) asParamTo transactionService::delete
    ) mapSuccess { item } thenInvokeAfter { it }

    override suspend fun get(): Res<Unit, List<Transaction>> {
        TODO("Not yet implemented")
    }

    private fun mapToDTO(trn: Transaction): TransactionDTO = TODO()
}