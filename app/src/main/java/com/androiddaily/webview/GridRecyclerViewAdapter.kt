package com.android.daily

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androiddaily.webview.databinding.GridItemBinding

class GridRecyclerViewAdapter(var context: Context, var list: ArrayList<GridInfo>) :
    RecyclerView.Adapter<GridRecyclerViewAdapter.GridRecyclerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridRecyclerHolder {
        return GridRecyclerHolder(GridItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: GridRecyclerHolder, position: Int) {
        holder.bindView(list[position].title)
        holder.itemView.setOnClickListener {
            context.startActivity(Intent(context, list[position].target))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class GridRecyclerHolder(var binding: GridItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(title: String) {
            binding.textview.text = title
        }
    }
}