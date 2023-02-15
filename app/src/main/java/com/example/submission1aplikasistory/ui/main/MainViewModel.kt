package com.example.submission1aplikasistory.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.submission1aplikasistory.data.Resource
import com.example.submission1aplikasistory.data.api.ApiConfig
import com.example.submission1aplikasistory.data.lokal.StoriesDao
import com.example.submission1aplikasistory.data.lokal.StoriesDatabase
import com.example.submission1aplikasistory.data.model.Stories
import com.example.submission1aplikasistory.data.model.response.BaseResponse
import com.example.submission1aplikasistory.data.model.response.StoriesResponse
import com.example.submission1aplikasistory.data.paging.StoriesRepository
import com.example.submission1aplikasistory.helper.UserPreferences
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: StoriesRepository): ViewModel() {

    fun getStories(): LiveData<PagingData<Stories>> =
        repository.getStories().cachedIn(viewModelScope)

}
