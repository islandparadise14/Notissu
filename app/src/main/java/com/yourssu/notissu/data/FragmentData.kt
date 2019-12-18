package com.yourssu.notissu.data

import android.os.Parcelable
import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class FragmentData(
    val fragment1: @RawValue Fragment,
    val fragment2: @RawValue Fragment,
    val fragment3: @RawValue Fragment
): Parcelable