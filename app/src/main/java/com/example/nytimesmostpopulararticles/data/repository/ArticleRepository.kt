package com.example.nytimesmostpopulararticles.data.repository

import androidx.lifecycle.LiveData
import com.example.nytimesmostpopulararticles.dtomodel.Article
import com.example.nytimesmostpopulararticles.utils.Resource

interface ArticleRepository {
    suspend fun fetchArticles(period: Int): Resource<List<Article>>

    fun observableArticleList(filter:String) : LiveData<List<Article>>
}