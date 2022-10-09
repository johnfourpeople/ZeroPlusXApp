package com.zeroplusx.mobile.ui.articles.adapter.item

import com.zeroplusx.mobile.domain.model.Article
import com.zeroplusx.mobile.ui.core.adapter.AdapterItem

class ArticleItem(val article: Article) : AdapterItem<Article>(article) {

    override fun areContentsTheSame(other: AdapterItem<*>): Boolean {
        other as ArticleItem
        return article == other.article
    }
}
