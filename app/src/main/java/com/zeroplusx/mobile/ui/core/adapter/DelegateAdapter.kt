package com.zeroplusx.mobile.ui.core.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class DelegateAdapter<T : AdapterItem<*>>(
    private val delegates: List<BaseItemDelegate<*, *>>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: List<Any> = emptyList()
    private var differ: AsyncListDiffer<T> = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<T>() {
            override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
                return oldItem.areItemsTheSame(newItem)
            }

            override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
                return oldItem.areContentsTheSame(newItem)
            }
        }
    )

    fun setItems(items: List<T>) {
        differ.submitList(items.takeIf { it.isNotEmpty() })
    }
    private var inflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegates[viewType].onCreateViewHolder(parent, getLayoutInflater(parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegates[holder.itemViewType].onBindViewHolder(holder, differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        delegates.forEachIndexed { index, delegate ->
            if (delegate.isApplicableForPosition(differ.currentList[position])) {
                return index
            }
        }
        throw IllegalStateException("No delegate that could")
    }

    override fun getItemId(position: Int): Long {
        val viewType = getItemViewType(position)
        val itemId = delegates[viewType].getItemId(differ.currentList[position])
        return (viewType.toLong() shl 32) or (itemId.toLong() and 0xFFFFFFFF)
    }

    private fun getLayoutInflater(view: View): LayoutInflater {
        inflater?.let { return it }
        val newInflater = LayoutInflater.from(view.context)
        inflater = newInflater
        return newInflater
    }
}
