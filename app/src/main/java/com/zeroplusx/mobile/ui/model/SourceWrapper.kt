package com.zeroplusx.mobile.ui.model

import android.os.Parcelable
import com.zeroplusx.mobile.domain.model.Source
import com.zeroplusx.mobile.ui.parcelable.SourceParceler
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler

@Parcelize
@TypeParceler<Source, SourceParceler>
class SourceWrapper(val source: Source) : Parcelable
