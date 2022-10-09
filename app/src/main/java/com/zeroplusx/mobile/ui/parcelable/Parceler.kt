package com.zeroplusx.mobile.ui.parcelable

import android.os.Parcel


fun Parcel.requireString(): String {
    return checkNotNull(readString())
}
