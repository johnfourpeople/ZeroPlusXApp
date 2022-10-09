package com.zeroplusx.mobile.ui.parcelable

import android.os.Parcel
import com.zeroplusx.mobile.domain.model.Source
import kotlinx.parcelize.Parceler


object SourceParceler : Parceler<Source> {

    override fun create(parcel: Parcel): Source {
        return Source(
            id = parcel.requireString(),
            title = parcel.requireString(),
            description = parcel.requireString(),
            url = parcel.requireString(),
        )
    }

    override fun Source.write(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(url)
    }
}
