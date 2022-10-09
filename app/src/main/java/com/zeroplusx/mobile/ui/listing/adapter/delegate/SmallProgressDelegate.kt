package com.zeroplusx.mobile.ui.listing.adapter.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zeroplusx.mobile.databinding.WidgetSmallProgressBinding
import com.zeroplusx.mobile.ui.core.adapter.BaseItemDelegate
import com.zeroplusx.mobile.ui.core.adapter.BindingViewHolder
import com.zeroplusx.mobile.ui.listing.adapter.item.SmallProgressItem

class SmallProgressDelegate : BaseItemDelegate<SmallProgressItem, BindingViewHolder<WidgetSmallProgressBinding>>(
    SmallProgressItem::class.java
) {

    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater): RecyclerView.ViewHolder {
        return BindingViewHolder(WidgetSmallProgressBinding.inflate(inflater, parent, false))
    }

    override fun bind(holder: BindingViewHolder<WidgetSmallProgressBinding>, item: SmallProgressItem) {}
}
