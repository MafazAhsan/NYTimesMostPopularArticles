package com.example.nytimesmostpopulararticles.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nytimesmostpopulararticles.data.repository.ArticleRepository
import com.example.nytimesmostpopulararticles.dtomodel.Article
import com.example.nytimesmostpopulararticles.utils.Resource
import com.example.nytimesmostpopulararticles.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(private val articleRepository: ArticleRepository) :
    ViewModel() {
    private val _networkErrorState = MutableLiveData<Boolean?>()
    val networkErrorState: LiveData<Boolean?> = _networkErrorState

    private val _navigateToArticleDetail = MutableLiveData<Article?>()
    val navigateToArticleDetail: LiveData<Article?> = _navigateToArticleDetail

    fun articleList(filter: String = "%%") = articleRepository.observableArticleList(filter)

    init {
        getArticleList()
    }

    private fun getArticleList() {
        viewModelScope.launch {
            val fetchArticles: Resource<List<Article>> = articleRepository.fetchArticles(7)
            _networkErrorState.postValue(
                when (fetchArticles.status) {
                    Status.ERROR -> {
                        articleList().value.isNullOrEmpty()
                    }
                    else -> false
                }
            )
        }
    }

    fun refreshForInitialDataFetch() {
        getArticleList()
    }

    fun resetNetworkErrorStatus() {
        _networkErrorState.value = null
    }

    fun showArticleDetail(article: Article) {
        _navigateToArticleDetail.value = article
    }

    fun doneNavigationToDetail() {
        _navigateToArticleDetail.value = null
    }
}