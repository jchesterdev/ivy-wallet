package com.ivy.core.ui.temp.trash

import com.ivy.base.R
import com.ivy.common.timeNowUTC
import com.ivy.data.planned.IntervalType
import java.time.LocalDateTime

@Deprecated("don't use, it's bad!")
data class LastNTimeRange(
    val periodN: Int,
    val periodType: IntervalType,
)

@Deprecated("don't use, it's bad!")
fun LastNTimeRange.fromDate(): LocalDateTime = periodType.incrementDate(
    date = timeNowUTC(),
    intervalN = -periodN.toLong()
)

@Deprecated("don't use, it's bad!")
fun LastNTimeRange.forDisplay(): String =
    "$periodN ${periodType.forDisplay(periodN)}"


@Deprecated("don't use, it's bad!")
fun IntervalType.forDisplay(intervalN: Int): String {
    val plural = intervalN > 1 || intervalN == 0
    return when (this) {
        IntervalType.DAY -> if (plural) com.ivy.core.ui.temp.stringRes(R.string.days) else com.ivy.core.ui.temp.stringRes(
            R.string.day
        )
        IntervalType.WEEK -> if (plural) com.ivy.core.ui.temp.stringRes(R.string.weeks) else com.ivy.core.ui.temp.stringRes(
            R.string.week
        )
        IntervalType.MONTH -> if (plural) com.ivy.core.ui.temp.stringRes(R.string.months) else com.ivy.core.ui.temp.stringRes(
            R.string.month
        )
        IntervalType.YEAR -> if (plural) com.ivy.core.ui.temp.stringRes(R.string.years) else com.ivy.core.ui.temp.stringRes(
            R.string.year
        )
    }
}

@Deprecated("don't use, it's bad!")
fun IntervalType.incrementDate(date: LocalDateTime, intervalN: Long): LocalDateTime {
    return when (this) {
        IntervalType.DAY -> date.plusDays(intervalN)
        IntervalType.WEEK -> date.plusWeeks(intervalN)
        IntervalType.MONTH -> date.plusMonths(intervalN)
        IntervalType.YEAR -> date.plusYears(intervalN)
    }
}