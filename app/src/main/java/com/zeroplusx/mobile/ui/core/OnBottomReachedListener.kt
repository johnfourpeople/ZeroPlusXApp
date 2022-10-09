package com.zeroplusx.mobile.ui.core

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OnBottomReachedListener(
    private val action: () -> Unit
) : RecyclerView.OnScrollListener() {

    private var invokeTimestamp = 0L

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        tryInvokeOnAnimationFinished(recyclerView)
    }

    private fun tryInvokeOnAnimationFinished(recyclerView: RecyclerView) {
        recyclerView.itemAnimator?.isRunning {
            invokeIfDebounced(recyclerView)
        } ?: invokeIfDebounced(recyclerView)
    }

    private fun invokeIfDebounced(recyclerView: RecyclerView) {
        if (System.currentTimeMillis() - invokeTimestamp >= 200L) {
            invokeOnBottomReached(recyclerView)
        }
    }

    private fun invokeOnBottomReached(recyclerView: RecyclerView) {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val totalItemCount = layoutManager.itemCount
        val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

        if (totalItemCount - lastVisibleItem <= 3) {
            invokeTimestamp = System.currentTimeMillis()
            action.invoke()
        }
    }
}
