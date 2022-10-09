package com.zeroplusx.mobile.ui.listing.adapter.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zeroplusx.mobile.databinding.WidgetErrorBinding
import com.zeroplusx.mobile.ui.core.adapter.BaseItemDelegate
import com.zeroplusx.mobile.ui.core.adapter.BindingViewHolder
import com.zeroplusx.mobile.ui.listing.adapter.item.ErrorItem

class ErrorDelegate : BaseItemDelegate<ErrorItem, BindingViewHolder<WidgetErrorBinding>>(ErrorItem::class.java) {

    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater): RecyclerView.ViewHolder {
        return BindingViewHolder(WidgetErrorBinding.inflate(inflater, parent, false))
    }

    override fun bind(holder: BindingViewHolder<WidgetErrorBinding>, item: ErrorItem) {
        holder.binding.errorTextView.text = item.error.message
    }
}
