package com.example.submission1aplikasistory.helper

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.example.submission1aplikasistory.data.Injection
import com.example.submission1aplikasistory.ui.addstories.AddStoriesViewModel
import com.example.submission1aplikasistory.ui.auth.AuthViewModel
import com.example.submission1aplikasistory.ui.main.MainViewModel
import com.example.submission1aplikasistory.ui.main.MainViewModelV2
import com.example.submission1aplikasistory.ui.map.MapsViewModel

class ViewModelFactory(private val pref: UserPreferences): ViewModelProvider.NewInstanceFactory() {

    private lateinit var mApplication: Application

    fun setApplication(application: Application) {
        mApplication = application
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            AuthViewModel::class.java -> AuthViewModel(pref) as T
//            MainViewModel::class.java -> MainViewModel(pref, mApplication) as T
            AddStoriesViewModel::class.java -> AddStoriesViewModel(pref) as T
            MapsViewModel::class.java -> MapsViewModel(pref, mApplication) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}