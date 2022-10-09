package com.zeroplusx.mobile.ui.listing.adapter.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
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
        with(holder.binding) {
            titleView.text = item.article.title
            descriptionView.text = item.article.description
            authorView.text = item.article.author
            publishedDayView.text = item.article.publishedDay.toString()
            publishedDayView.isVisible = item.article.publishedDay != null
            if (!item.article.thumbnail.isNullOrEmpty()) {
                thumbnailView.load(
                    item.article.thumbnail,
                    builder = { placeholder(R.drawable.image_placeholder) })
                thumbnailView.isVisible = true
            } else {
                thumbnailView.isVisible = false
            }

        }
        holder.setData(item.article)
    }

    override fun itemId(item: ArticleItem): Int {
        return item.article.hashCode()
    }
}
