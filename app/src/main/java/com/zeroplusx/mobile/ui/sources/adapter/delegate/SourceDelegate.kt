package com.zeroplusx.mobile.ui.sources.adapter.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zeroplusx.mobile.databinding.WidgetSourceBinding
import com.zeroplusx.mobile.domain.model.Source
import com.zeroplusx.mobile.ui.core.adapter.BaseItemDelegate
import com.zeroplusx.mobile.ui.core.adapter.BindingViewHolder
import com.zeroplusx.mobile.ui.sources.adapter.item.SourceItem

class SourceDelegate(
    private val onClick: (Source) -> Unit
) : BaseItemDelegate<SourceItem, BindingViewHolder<WidgetSourceBinding>>(SourceItem::class.java) {

    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater): RecyclerView.ViewHolder {
        return BindingViewHolder(WidgetSourceBinding.inflate(inflater, parent, false))
            .also { viewHolder ->
                viewHolder.itemView.setOnClickListener { onClick.invoke(viewHolder.requireData()) }
            }
    }

    override fun bind(holder: BindingViewHolder<WidgetSourceBinding>, item: SourceItem) {
        holder.binding.titleView.text = item.source.title
        holder.binding.descriptionView.text = item.source.description
        holder.binding.urlView.text = item.source.url
        holder.setData(item.source)
    }
}
