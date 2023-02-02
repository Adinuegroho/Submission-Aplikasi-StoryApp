package com.example.submission1aplikasistory.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission1aplikasistory.R
import com.example.submission1aplikasistory.data.model.Stories
import com.example.submission1aplikasistory.databinding.ItemStoryBinding

class MainAdapter(private val callback: StoriesCallback): RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private val mData = ArrayList<Stories>()

    fun setData(stories: ArrayList<Stories>) {
        mData.clear()
        mData.addAll(stories)
        notifyDataSetChanged()
    }

    interface StoriesCallback {
        fun onStoryClicked(story: Stories, itemBinding: ItemStoryBinding)
    }

    inner class MainViewHolder(private val binding: ItemStoryBinding):
        RecyclerView.ViewHolder(binding.root){
            fun bind(story: Stories) {
                with(binding) {
                    Glide.with(imgStory)
                        .load(story.photoUrl)
                        .centerCrop()
                        .apply(RequestOptions.placeholderOf(R.drawable.placeholder))
                        .into(imgStory)
                    tvName.text = story.name
                    root.setOnClickListener{ callback.onStoryClicked(story, this)}
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder = MainViewHolder(
        ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) = holder.bind(mData[position])

    override fun getItemCount(): Int = mData.size
}