package com.zeroplusx.mobile.ui.core.parceler

import android.os.Parcel


fun Parcel.requireString(): String {
    return checkNotNull(readString())
}
