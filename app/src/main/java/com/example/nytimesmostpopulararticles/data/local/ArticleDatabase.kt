package com.example.nytimesmostpopulararticles.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.nytimesmostpopulararticles.dtomodel.Article
import com.example.nytimesmostpopulararticles.dtomodel.Media
import com.example.nytimesmostpopulararticles.dtomodel.MediaMetadata

@Database(
    version = 1,
    entities = [Article::class, Media::class, MediaMetadata::class],
    exportSchema = true
)
@TypeConverters(RoomConverters::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}