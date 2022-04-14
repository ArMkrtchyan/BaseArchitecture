package com.example.basearchitecture.mvi

sealed interface ViewIntent {
    object InIt : ViewIntent
}