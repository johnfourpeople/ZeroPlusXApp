package com.zeroplusx.mobile.ui.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseItemDelegate<Item, ViewHolder : RecyclerView.ViewHolder>(
    private val itemClass: Class<Item>,
) {
    fun isApplicableForPosition(item: Any): Boolean {
        return itemClass.isInstance(item)
    }

    abstract fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater): RecyclerView.ViewHolder

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, data: Any) {
        @Suppress("UNCHECKED_CAST")
        bind(holder as ViewHolder, data as Item)
    }

    abstract fun bind(holder: ViewHolder, item: Item)
}
