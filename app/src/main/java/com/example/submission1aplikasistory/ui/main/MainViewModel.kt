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

    private var storiesDao: StoriesDao? = null
//    private var storiesDb: StoriesDatabase? = StoriesDatabase.getDatabase(application)
    private val _stories = MutableLiveData<Resource<ArrayList<Stories>>>()
    val stories: LiveData<Resource<ArrayList<Stories>>> = _stories

//    init {
//        storiesDao = storiesDb?.storiesDao()
//    }

    suspend fun getStories():LiveData<PagingData<Stories>> =
        repository.getStories().cachedIn(viewModelScope)


//    suspend fun getStories() {
//        _stories.postValue(Resource.Loading())
//        val client = ApiConfig.apiInstance.getStories(token = "Bearer ${preferences.getUserKey().first()}")
//
//        client.enqueue(object : Callback<StoriesResponse> {
//            override fun onResponse(
//                call: Call<StoriesResponse>,
//                response: Response<StoriesResponse>
//            ) {
//                if (response.isSuccessful) {
//                    response.body()?.let {
//                        val listStories = it.listStory
//
//                        viewModelScope.launch {
//                            storiesDao?.deleteAll()
//                            listStories.forEach { stories ->
//                                storiesDao?.insert(stories)
//                            }
//                        }
//                        _stories.postValue(Resource.Success(ArrayList(listStories)))
//                    }
//                } else {
//                    val errorResponse = Gson().fromJson(
//                        response.errorBody()?.charStream(),
//                        BaseResponse::class.java
//                    )
//                    _stories.postValue(Resource.Error(errorResponse.message))
//                }
//            }
//
//            override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
//                Log.e(
//                    MainViewModel::class.java.simpleName,
//                    "onFailure getStories"
//                )
//                _stories.postValue(Resource.Error(t.message))
//            }
//        })
//    }
}