package com.zeroplusx.mobile.ui.core.model

import android.os.Parcelable
import com.zeroplusx.mobile.domain.model.Source
import com.zeroplusx.mobile.ui.core.parceler.SourceParceler
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler

@Parcelize
@TypeParceler<Source, SourceParceler>
class SourceWrapper(val source: Source) : Parcelable
