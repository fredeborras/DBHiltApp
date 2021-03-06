package com.dbhiltapp.app.feature.main.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dbhiltapp.app.databinding.ItemMainListBinding
import com.dbhiltapp.app.feature.main.entities.Hit

class MainListAdapter(private val items: List<Hit>, private val callback: OnItemClick) :
    RecyclerView.Adapter<MainListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMainListBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = this.items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(this.items[position], this.callback)

    inner class ViewHolder(private val binding: ItemMainListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Hit, callback: OnItemClick) {
            binding.item = item
            binding.onClick = callback
            binding.executePendingBindings()
        }
    }
}