package com.example.nytimesmostpopulararticles.data.remote

import com.example.nytimesmostpopulararticles.BuildConfig
import com.example.nytimesmostpopulararticles.dtomodel.ArticleList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArticleListApi {
    @GET("all-sections/{period}.json")
    suspend fun fetchArticles(
        @Path("period") period: Int = 7, // weekly
        @Query("api-key") apiKey: String = "y2Q4RePNC5yAK1vDiJbGaYhzwIkJ5QGf"
    ): Response<ArticleList>
}

