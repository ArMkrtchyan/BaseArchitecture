package com.example.core.mvi

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface State : Parcelable {
    @Parcelize
    object LoadingButtonState : State, Parcelable

    @Parcelize
    object LoadingState : State, Parcelable

    @Parcelize
    class ErrorState(val throwable: Throwable) : State, Parcelable

    @Parcelize
    object EmptyState : State, Parcelable

    @Parcelize
    object Success : State, Parcelable

    @Parcelize
    object None : State, Parcelable
}