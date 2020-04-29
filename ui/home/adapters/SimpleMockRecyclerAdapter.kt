package com.example.iplayer.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

class SimpleMockRecyclerAdapter(
    @LayoutRes val layoutId: Int
) : RecyclerView.Adapter<SimpleMockRecyclerAdapter.MockViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MockViewHolder {
        return MockViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))
    }

    override fun getItemCount(): Int = 20

    override fun onBindViewHolder(holder: MockViewHolder, position: Int) {

    }

    class MockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}