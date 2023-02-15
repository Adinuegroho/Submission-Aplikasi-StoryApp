package com.example.submission1aplikasistory.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.submission1aplikasistory.data.Injection
import com.example.submission1aplikasistory.data.model.Stories
import com.example.submission1aplikasistory.data.paging.StoriesRepository

@ExperimentalPagingApi
class MainViewModelV2(private val repository: StoriesRepository): ViewModel() {

     suspend fun getStories():LiveData<PagingData<Stories>> =
        repository.getStories().cachedIn(viewModelScope)

}

//class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
////            return MainViewModel(Injection.provideRepository(context)) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}