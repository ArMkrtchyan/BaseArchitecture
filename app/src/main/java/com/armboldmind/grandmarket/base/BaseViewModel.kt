package com.armboldmind.grandmarket.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    protected val _uiState = MutableLiveData(UIState.LOADING)
    val uiState: LiveData<UIState>
        get() = _uiState

}