package com.zeroplusx.mobile.ui.sources.adapter.item

import com.zeroplusx.mobile.domain.model.Source
import com.zeroplusx.mobile.ui.core.adapter.AdapterItem

class SourceItem(val source: Source) : AdapterItem<Source>(source) {

    override fun areContentsTheSame(other: AdapterItem<*>): Boolean {
        other as SourceItem
        return source == other.source
    }
}
