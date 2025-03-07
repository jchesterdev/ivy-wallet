package com.ivy.wallet.domain.deprecated.logic

import androidx.compose.ui.graphics.toArgb
import com.ivy.base.AccountBalance
import com.ivy.base.R
import com.ivy.data.AccountOld
import com.ivy.data.CategoryOld
import com.ivy.design.l0_system.color.*
import com.ivy.wallet.domain.deprecated.logic.model.CreateAccountData
import com.ivy.wallet.domain.deprecated.logic.model.CreateCategoryData
import com.ivy.wallet.io.persistence.dao.AccountDao
import com.ivy.wallet.io.persistence.dao.CategoryDao
import com.ivy.wallet.io.persistence.data.toEntity

@Deprecated("Migrate to FP Style")
class PreloadDataLogic(
    private val accountsDao: AccountDao,
    private val categoryDao: CategoryDao
) {
    var categoryOrderNum = 0.0

    fun shouldPreloadData(accounts: List<AccountBalance>): Boolean {
        //Preload data only if the user has less than 2 accounts
        return accounts.size < 2
    }

    suspend fun preloadAccounts() {
        val cash = AccountOld(
            name = com.ivy.core.ui.temp.stringRes(R.string.cash),
            currency = null,
            color = Green.toArgb(),
            icon = "cash",
            orderNum = 0.0,
            isSynced = false
        )

        val bank = AccountOld(
            name = com.ivy.core.ui.temp.stringRes(R.string.bank),
            currency = null,
            color = IvyDark.toArgb(),
            icon = "bank",
            orderNum = 1.0,
            isSynced = false
        )

        accountsDao.save(cash.toEntity())
        accountsDao.save(bank.toEntity())
    }

    fun accountSuggestions(baseCurrency: String): List<CreateAccountData> = listOf(
        CreateAccountData(
            name = com.ivy.core.ui.temp.stringRes(R.string.cash),
            currency = baseCurrency,
            color = Green,
            icon = "cash",
            balance = 0.0
        ),
        CreateAccountData(
            name = com.ivy.core.ui.temp.stringRes(R.string.bank),
            currency = baseCurrency,
            color = IvyDark,
            icon = "bank",
            balance = 0.0
        ),
        CreateAccountData(
            name = com.ivy.core.ui.temp.stringRes(R.string.revoult),
            currency = baseCurrency,
            color = Blue,
            icon = "revolut",
            balance = 0.0
        ),
    )

    suspend fun preloadCategories() {
        categoryOrderNum = 0.0

        val categoriesToPreload = preloadCategoriesCreateData()

        for (createData in categoriesToPreload) {
            preloadCategory(createData)
        }
    }

    private fun preloadCategoriesCreateData() = listOf(
        CreateCategoryData(
            name = com.ivy.core.ui.temp.stringRes(R.string.food_drinks),
            color = Green,
            icon = "fooddrink"
        ),

        CreateCategoryData(
            name = com.ivy.core.ui.temp.stringRes(R.string.bills_fees),
            color = Red,
            icon = "bills"
        ),

        CreateCategoryData(
            name = com.ivy.core.ui.temp.stringRes(R.string.transport),
            color = YellowLight,
            icon = "transport"
        ),

        CreateCategoryData(
            name = com.ivy.core.ui.temp.stringRes(R.string.groceries),
            color = GreenLight,
            icon = "groceries"
        ),

        CreateCategoryData(
            name = com.ivy.core.ui.temp.stringRes(R.string.entertainment),
            color = Orange,
            icon = "game"
        ),

        CreateCategoryData(
            name = com.ivy.core.ui.temp.stringRes(R.string.shopping),
            color = Purple,
            icon = "shopping"
        ),

        CreateCategoryData(
            name = com.ivy.core.ui.temp.stringRes(R.string.gifts),
            color = RedLight,
            icon = "gift"
        ),

        CreateCategoryData(
            name = com.ivy.core.ui.temp.stringRes(R.string.health),
            color = IvyLight,
            icon = "health"
        ),

        CreateCategoryData(
            name = com.ivy.core.ui.temp.stringRes(R.string.investments),
            color = IvyDark,
            icon = "leaf"
        ),

        CreateCategoryData(
            name = com.ivy.core.ui.temp.stringRes(R.string.loans),
            color = BlueDark,
            icon = "loan"
        ),
    )

    private suspend fun preloadCategory(
        data: CreateCategoryData
    ) {
        val category = CategoryOld(
            name = data.name,
            color = data.color.toArgb(),
            icon = data.icon,
            orderNum = categoryOrderNum++,
            isSynced = false
        )

        categoryDao.save(category.toEntity())
    }

    fun categorySuggestions(): List<CreateCategoryData> = preloadCategoriesCreateData()
        .plus(
            listOf(
                CreateCategoryData(
                    name = com.ivy.core.ui.temp.stringRes(R.string.car),
                    color = Blue3,
                    icon = "vehicle"
                ),

                CreateCategoryData(
                    name = com.ivy.core.ui.temp.stringRes(R.string.work),
                    color = Blue2Light,
                    icon = "work"
                ),

                CreateCategoryData(
                    name = com.ivy.core.ui.temp.stringRes(R.string.home_category),
                    color = Green2,
                    icon = "house"
                ),

                CreateCategoryData(
                    name = com.ivy.core.ui.temp.stringRes(R.string.restaurant),
                    color = Orange3,
                    icon = "restaurant"
                ),

                CreateCategoryData(
                    name = com.ivy.core.ui.temp.stringRes(R.string.family),
                    color = Red3Light,
                    icon = "family"
                ),

                CreateCategoryData(
                    name = com.ivy.core.ui.temp.stringRes(R.string.social_life),
                    color = Blue2,
                    icon = "people"
                ),

                CreateCategoryData(
                    name = com.ivy.core.ui.temp.stringRes(R.string.order_food),
                    color = Orange2,
                    icon = "orderfood2"
                ),

                CreateCategoryData(
                    name = com.ivy.core.ui.temp.stringRes(R.string.travel),
                    color = BlueLight,
                    icon = "travel"
                ),

                CreateCategoryData(
                    name = com.ivy.core.ui.temp.stringRes(R.string.fitness),
                    color = Purple2,
                    icon = "fitness"
                ),

                CreateCategoryData(
                    name = com.ivy.core.ui.temp.stringRes(R.string.self_development),
                    color = Yellow,
                    icon = "selfdevelopment"
                ),

                CreateCategoryData(
                    name = com.ivy.core.ui.temp.stringRes(R.string.clothes),
                    color = Green2Light,
                    icon = "clothes2"
                ),

                CreateCategoryData(
                    name = com.ivy.core.ui.temp.stringRes(R.string.beauty),
                    color = Red3,
                    icon = "makeup"
                ),

                CreateCategoryData(
                    name = com.ivy.core.ui.temp.stringRes(R.string.education),
                    color = Blue,
                    icon = "education"
                ),

                CreateCategoryData(
                    name = com.ivy.core.ui.temp.stringRes(R.string.pet),
                    color = Orange3Light,
                    icon = "pet"
                ),

                CreateCategoryData(
                    name = com.ivy.core.ui.temp.stringRes(R.string.sports),
                    color = Purple1,
                    icon = "sports"
                ),
            )
        )


}

