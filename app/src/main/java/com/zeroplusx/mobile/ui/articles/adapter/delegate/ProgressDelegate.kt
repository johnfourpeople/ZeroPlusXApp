package com.zeroplusx.mobile.ui.articles.adapter.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zeroplusx.mobile.databinding.WidgetProgressBinding
import com.zeroplusx.mobile.ui.core.adapter.BaseItemDelegate
import com.zeroplusx.mobile.ui.core.adapter.BindingViewHolder
import com.zeroplusx.mobile.ui.articles.adapter.item.ProgressItem

class ProgressDelegate : BaseItemDelegate<ProgressItem, BindingViewHolder<WidgetProgressBinding>>(
    ProgressItem::class.java
) {

    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater): RecyclerView.ViewHolder {
        return BindingViewHolder(WidgetProgressBinding.inflate(inflater, parent, false))
    }

    override fun bind(holder: BindingViewHolder<WidgetProgressBinding>, item: ProgressItem) {}
}
