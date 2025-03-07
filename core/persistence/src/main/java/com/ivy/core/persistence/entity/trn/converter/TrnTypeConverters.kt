package com.ivy.core.persistence.entity.trn.converter

import androidx.room.TypeConverter
import com.ivy.core.persistence.entity.trn.data.TrnTimeType
import com.ivy.data.transaction.TrnPurpose
import com.ivy.data.transaction.TrnType

class TrnTypeConverters {

    // region BatchPurpose
    @TypeConverter
    fun ser(purpose: TrnPurpose?): Int? = purpose?.code

    @TypeConverter
    fun purpose(code: Int?): TrnPurpose? = code?.let(TrnPurpose::fromCode)
    // endregion

    // region TrnType
    @TypeConverter
    fun ser(type: TrnType): Int = type.code

    @TypeConverter
    fun trnType(code: Int): TrnType = TrnType.fromCode(code)!!
    // endregion

    // region TrnTimeType
    @TypeConverter
    fun ser(timeType: TrnTimeType): Int = timeType.code

    @TypeConverter
    fun trnTimeType(code: Int): TrnTimeType =
        TrnTimeType.fromCode(code) ?: TrnTimeType.Actual
    // endregion
}