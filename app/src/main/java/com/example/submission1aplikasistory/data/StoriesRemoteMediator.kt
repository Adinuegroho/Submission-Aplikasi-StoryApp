package com.example.submission1aplikasistory.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.submission1aplikasistory.data.api.ApiService
import com.example.submission1aplikasistory.data.lokal.StoriesDatabase
import com.example.submission1aplikasistory.data.model.Stories

@OptIn(ExperimentalPagingApi::class)
class StoriesRemoteMediator(
    private val database: StoriesDatabase,
    private val apiService: ApiService,
    private val token: String
): RemoteMediator<Int, Stories>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Stories>
    ): MediatorResult {
        val page = INITIAL_PAGE_INDEX

        try {
            val responseData = apiService.getStories(token, page, state.config.pageSize)

            val endOfPaginationReached = responseData.listStory.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
//                    database.remoteKeysDao().deleteRemoteKeys()
                    database.storiesDao().deleteAll()
                }
//                val prevKey = if (page == 1) null else page - 1
//                val nextKey = if (endOfPaginationReached) null else page + 1
//                val keys = responseData.map {
//                    RemoteKeys(id = it.id, prevKey = prevKey, nextKey = nextKey)
//                }
//                database.storiesDao().insert(keys)
                responseData.listStory.forEach { storiesResponItem ->
                    val story = Stories(
                        storiesResponItem.id,
                        storiesResponItem.name,
                        storiesResponItem.photoUrl,
                        storiesResponItem.createdAt,
                        storiesResponItem.description,
                        storiesResponItem.lon,
                        storiesResponItem.lat,
                    )

                database.storiesDao().insert(story)
                }

            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}
