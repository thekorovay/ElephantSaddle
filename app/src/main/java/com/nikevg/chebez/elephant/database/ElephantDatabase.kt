package com.nikevg.chebez.elephant.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Elephant::class],
    version = 1
)
abstract class ElephantDatabase : RoomDatabase() {

    abstract fun elephantDao(): ElephantDao

    companion object {
        const val DATABASE_NAME = "elephants"
    }
}
