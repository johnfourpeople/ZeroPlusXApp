package com.zeroplusx.mobile.ui.core.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class BindingViewHolder<T : ViewBinding>(
    val binding: T
) : RecyclerView.ViewHolder(binding.root) {
    private var data: Any? = null

    fun setData(data: Any) {
        this.data = data
    }

    fun <D> requireData(): D {
        @Suppress("UNCHECKED_CAST")
        return data as D
    }
}
