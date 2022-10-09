package com.zeroplusx.mobile.ui.core.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DelegateAdapter(
    private val delegates: List<BaseItemDelegate<*, *>>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: List<Any> = emptyList()
    private var inflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegates[viewType].onCreateViewHolder(parent, getLayoutInflater(parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegates[holder.itemViewType].onBindViewHolder(holder, data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        delegates.forEachIndexed { index, delegate ->
            if (delegate.isApplicableForPosition(data[position])) {
                return index
            }
        }
        throw IllegalStateException("No delegate that could")
    }

    private fun getLayoutInflater(view: View): LayoutInflater {
        inflater?.let { return it }
        val newInflater = LayoutInflater.from(view.context)
        inflater = newInflater
        return newInflater
    }
}
