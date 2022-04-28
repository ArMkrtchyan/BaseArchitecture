package com.example.core.mvi

sealed interface ViewIntent {
    object InIt : ViewIntent
}