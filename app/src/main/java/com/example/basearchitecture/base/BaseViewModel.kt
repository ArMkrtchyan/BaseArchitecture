package com.example.basearchitecture.base

import androidx.lifecycle.ViewModel
import com.example.basearchitecture.mvi.ViewIntent
import com.example.basearchitecture.ui.models.UiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<T : UiModel> : ViewModel() {
    protected val _uiState by lazy { MutableStateFlow<T?>(null) }
    val uiState: StateFlow<T?>
        get() = _uiState

    abstract fun sendIntent(intent: ViewIntent)

}