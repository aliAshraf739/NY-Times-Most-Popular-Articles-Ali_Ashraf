package com.ali.ashraf.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ali.ashraf.mvvm.data.model.article.GetArticlesResponse
import com.ali.ashraf.mvvm.data.repository.MainRepository
import com.ali.ashraf.mvvm.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var article:LiveData<Resource<GetArticlesResponse>>? = null

    init {
        val mainRepository = MainRepository(application)
        fetchArticles(mainRepository)
        article = mainRepository.getArticlesLiveData()
    }

    private fun fetchArticles(mainRepository: MainRepository) {
        viewModelScope.launch(Dispatchers.Main) {
            mainRepository.getArticlesFromServer()
        }
    }

    fun getArticles(): LiveData<Resource<GetArticlesResponse>>? {
        return article
    }

}