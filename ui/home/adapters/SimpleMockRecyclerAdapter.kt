package com.example.iplayer.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.iplayer.R

class SimpleMockRecyclerAdapter(
    val mockType : Int = 0
) : RecyclerView.Adapter<SimpleMockRecyclerAdapter.MockViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MockViewHolder {
        val layoutItem = if (mockType == 0) R.layout.item_single_album_somg else R.layout.item_mock_two

        return MockViewHolder(
            LayoutInflater.from(parent.context).inflate(layoutItem, parent, false)
        )
    }

    override fun getItemCount(): Int = 20

    override fun onBindViewHolder(holder: MockViewHolder, position: Int) {

    }

    class MockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}