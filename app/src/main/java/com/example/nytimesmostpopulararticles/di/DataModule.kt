package com.example.nytimesmostpopulararticles.di

import android.content.Context
import androidx.room.Room
import com.example.nytimesmostpopulararticles.data.local.ArticleDao
import com.example.nytimesmostpopulararticles.data.local.ArticleDatabase
import com.example.nytimesmostpopulararticles.data.local.RoomConverters
import com.example.nytimesmostpopulararticles.utils.DATABASE_NAME
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun typeConverter() = RoomConverters(Moshi.Builder().build())

    @Singleton
    @Provides
    fun articleDao(
        @ApplicationContext context: Context,
        typeConverter: RoomConverters
    ): ArticleDao =
        Room.databaseBuilder(
            context,
            ArticleDatabase::class.java,
            DATABASE_NAME
        ).addTypeConverter(typeConverter).build().articleDao()
}