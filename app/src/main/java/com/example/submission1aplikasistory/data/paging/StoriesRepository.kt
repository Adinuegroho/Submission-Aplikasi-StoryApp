package com.example.submission1aplikasistory.data.paging

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.submission1aplikasistory.data.api.ApiConfig
import com.example.submission1aplikasistory.data.api.ApiService
import com.example.submission1aplikasistory.data.lokal.StoriesDatabase
import com.example.submission1aplikasistory.data.model.Stories
import com.example.submission1aplikasistory.helper.UserPreferences
import kotlinx.coroutines.flow.first

class StoriesRepository(
    private val storiesDatabase: StoriesDatabase,
    private val apiService: ApiService,
//    private val userPreferences: UserPreferences
    private val token: String
) {
    suspend fun getStories(): LiveData<PagingData<Stories>> {
//        val token = "Bearer ${userPreferences.getUserKey().first()}"

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoriesRemoteMediator(storiesDatabase, apiService, token),
            pagingSourceFactory = {
                storiesDatabase.storiesDao().findAll()
            }
        ).liveData
    }
}