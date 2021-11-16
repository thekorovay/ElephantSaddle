package com.nikevg.chebez.elephant.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Elephant.TABLE_NAME)
data class Elephant(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    val id: Int,
    @ColumnInfo(name = COLUMN_NAME_NAME)
    val nameRes: Int,
    @ColumnInfo(name = COLUMN_DRAWABLE_NAME)
    val drawableRes: Int
) {

    companion object {
        const val TABLE_NAME = "elephants"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME_NAME = "name_res"
        const val COLUMN_DRAWABLE_NAME = "drawable_res"
    }
}
