package com.example.submission1aplikasistory.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.submission1aplikasistory.R
import com.example.submission1aplikasistory.data.Resource
import com.example.submission1aplikasistory.data.model.Stories
import com.example.submission1aplikasistory.databinding.ActivityMainBinding
import com.example.submission1aplikasistory.databinding.ItemStoryBinding
import com.example.submission1aplikasistory.helper.UserPreferences
import com.example.submission1aplikasistory.helper.ViewModelFactory
import com.example.submission1aplikasistory.helper.ViewModelStoryFactory
import com.example.submission1aplikasistory.ui.addstories.AddStoriesActivity
import com.example.submission1aplikasistory.ui.auth.AuthViewModel
import com.example.submission1aplikasistory.ui.auth.LoginActivity
import com.example.submission1aplikasistory.ui.detail.DetailActivity
import com.example.submission1aplikasistory.ui.map.MapsActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), MainAdapter.StoriesCallback {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_key")
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val mainAdapter = MainAdapter()
    private lateinit var mainViewModel: MainViewModel
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = resources.getString(R.string.home)

        setupViewModel()
        setupView()
    }

    override fun onStoryClicked(story: Stories, itemBinding: ItemStoryBinding) {
        val optionsCompat: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            Pair(itemBinding.imgStory, "imageDetail"),
            Pair(itemBinding.tvName, "nameDetail"),
        )
        val detailIntent = Intent(this, DetailActivity::class.java)
        detailIntent.putExtra(DetailActivity.EXTRA_DATA, story)
        startActivity(detailIntent, optionsCompat.toBundle())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                authViewModel.logout()
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finishAffinity()
                true
            }
            R.id.maps -> {
                startActivity(Intent(this, MapsActivity::class.java))
                true
            }
            else -> false
        }
    }

    private fun setupViewModel() {
        val pref = UserPreferences.getInstance(dataStore)
        val viewModelFactory = ViewModelFactory(pref)
        viewModelFactory.setApplication(application)

        authViewModel = ViewModelProvider(this, ViewModelFactory(pref))[AuthViewModel::class.java]
        mainViewModel = ViewModelProvider(this, ViewModelStoryFactory(this))[MainViewModel::class.java]

        mainViewModel.getStories().observe(this) {
            mainAdapter.submitData(lifecycle, it)
//            when (it) {
//                is Resource.Success -> {
//                    it.data?.let { stories -> mainAdapter.setData(stories) }
//                    showLoading(false)
//                }
//                is Resource.Loading -> showLoading(true)
//                is Resource.Error -> {
//                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
//                    showLoading(false)
//                }
//            }
        }

        fetchData()
    }

    private fun fetchData() {
        CoroutineScope(Dispatchers.IO).launch {
            mainViewModel.getStories()
        }
    }

    private fun setupView() {
        setupRecylerView()
        fab()
    }

    private fun fab() {
        binding.fabFav.setOnClickListener{
            val addIntent = Intent(this, AddStoriesActivity::class.java)
            startActivity(addIntent)
        }
    }

    private fun setupRecylerView() {
        with(binding.rvStory) {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this@MainActivity, RV_COLOMN_COUNT)
            adapter = mainAdapter
        }
    }

    private fun showLoading(state: Boolean) {
        binding.isLoading.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {
        const val RV_COLOMN_COUNT = 1
    }
}
