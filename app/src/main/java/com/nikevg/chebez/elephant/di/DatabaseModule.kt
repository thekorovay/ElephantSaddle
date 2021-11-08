package com.nikevg.chebez.elephant.di

import android.content.Context
import androidx.room.Room
import com.nikevg.chebez.elephant.database.ElephantDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideElephantDatabase(
        @ApplicationContext context: Context
    ): ElephantDatabase = Room.databaseBuilder(
        context,
        ElephantDatabase::class.java,
        ElephantDatabase.DATABASE_NAME
    ).build()
}
