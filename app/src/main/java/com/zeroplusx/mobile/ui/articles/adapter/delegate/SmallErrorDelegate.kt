package com.zeroplusx.mobile.ui.articles.adapter.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zeroplusx.mobile.databinding.WidgetSmallErrorBinding
import com.zeroplusx.mobile.ui.core.adapter.BaseItemDelegate
import com.zeroplusx.mobile.ui.core.adapter.BindingViewHolder
import com.zeroplusx.mobile.ui.articles.adapter.item.SmallErrorItem

class SmallErrorDelegate(
    private val onClick: (() -> Unit)? = null
) : BaseItemDelegate<SmallErrorItem, BindingViewHolder<WidgetSmallErrorBinding>>(SmallErrorItem::class.java) {

    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater): RecyclerView.ViewHolder {
        return BindingViewHolder(WidgetSmallErrorBinding.inflate(inflater, parent, false)).apply {
            onClick?.let { onClick -> itemView.setOnClickListener { onClick.invoke() } }
        }
    }

    override fun bind(holder: BindingViewHolder<WidgetSmallErrorBinding>, item: SmallErrorItem) {
        holder.binding.errorTextView.text = item.error.message
    }
}
