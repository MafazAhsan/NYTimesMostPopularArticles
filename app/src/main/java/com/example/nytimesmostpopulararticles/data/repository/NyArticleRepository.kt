package com.example.nytimesmostpopulararticles.data.repository

import androidx.lifecycle.LiveData
import com.example.nytimesmostpopulararticles.data.local.ArticleDao
import com.example.nytimesmostpopulararticles.data.remote.ArticleListApi
import com.example.nytimesmostpopulararticles.dtomodel.Article
import com.example.nytimesmostpopulararticles.dtomodel.ArticleList
import com.example.nytimesmostpopulararticles.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NyArticleRepository @Inject constructor(
    private val articleDao: ArticleDao,
    private val articleListApi: ArticleListApi
) : ArticleRepository {
    override suspend fun fetchArticles(period: Int): Resource<List<Article>> {
        return try {
            withContext(Dispatchers.IO) {
                Resource.loading(null)
                val articleListResponse: Response<ArticleList> =
                    articleListApi.fetchArticles(period)
                if (articleListResponse.isSuccessful) {
                    articleListResponse.body()?.let {
                        // if successful response insert into db
                        it.let {
                            articleDao.insertArticleList(it.results)
                        }
                        return@let Resource.success(null)
                    } ?: Resource.error("Some unknown error occurred", null)
                } else {
                    Resource.error("Some unknown error occurred", null)
                }
            }
        } catch (e: Exception) {
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }
    }

    override fun observableArticleList(filter: String): LiveData<List<Article>> =
        articleDao.getAllArticles(filter)
}