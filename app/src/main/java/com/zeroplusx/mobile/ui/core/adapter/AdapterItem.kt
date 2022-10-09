package com.zeroplusx.mobile.ui.core.adapter

abstract class AdapterItem<T>(val id: T) {
    fun areItemsTheSame(other: AdapterItem<*>): Boolean {
        return javaClass == other.javaClass && id == other.id
    }

    open fun areContentsTheSame(other: AdapterItem<*>) = false
}
