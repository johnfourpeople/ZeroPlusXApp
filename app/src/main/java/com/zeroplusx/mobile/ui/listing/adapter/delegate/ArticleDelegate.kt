package com.zeroplusx.mobile.ui.listing.adapter.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.zeroplusx.mobile.R
import com.zeroplusx.mobile.databinding.WidgetArticleBinding
import com.zeroplusx.mobile.ui.core.adapter.BaseItemDelegate
import com.zeroplusx.mobile.ui.core.adapter.BindingViewHolder
import com.zeroplusx.mobile.ui.listing.adapter.item.ArticleItem

class ArticleDelegate : BaseItemDelegate<ArticleItem, BindingViewHolder<WidgetArticleBinding>>(
    ArticleItem::class.java
) {

    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater): RecyclerView.ViewHolder {
        return BindingViewHolder(WidgetArticleBinding.inflate(inflater, parent, false))
    }

    override fun bind(holder: BindingViewHolder<WidgetArticleBinding>, item: ArticleItem) {
        holder.binding.titleView.text = item.article.title
        holder.binding.descriptionView.text = item.article.description
        holder.binding.authorView.text = item.article.author
        holder.binding.publishedDayView.text = item.article.publishedDay
        holder.binding.thumbnailView.load(
            item.article.thumbnail,
            builder = { placeholder(R.drawable.image_placeholder) })
        holder.setData(item.article)
    }

    override fun itemId(item: ArticleItem): Int {
        return item.article.hashCode()
    }
}
