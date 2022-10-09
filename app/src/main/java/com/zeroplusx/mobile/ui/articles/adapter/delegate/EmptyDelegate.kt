package com.zeroplusx.mobile.ui.articles.adapter.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zeroplusx.mobile.databinding.WidgetEmptyBinding
import com.zeroplusx.mobile.ui.articles.adapter.item.EmptyItem
import com.zeroplusx.mobile.ui.core.adapter.BaseItemDelegate
import com.zeroplusx.mobile.ui.core.adapter.BindingViewHolder

class EmptyDelegate : BaseItemDelegate<EmptyItem, BindingViewHolder<WidgetEmptyBinding>>(
    EmptyItem::class.java
) {

    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater): RecyclerView.ViewHolder {
        return BindingViewHolder(WidgetEmptyBinding.inflate(inflater, parent, false))
    }

    override fun bind(holder: BindingViewHolder<WidgetEmptyBinding>, item: EmptyItem) {}
}

