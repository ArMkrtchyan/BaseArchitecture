package com.example.core.mvi

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface Action : Parcelable {
    @Parcelize
    object ToTheNextPage : Action, Parcelable
}