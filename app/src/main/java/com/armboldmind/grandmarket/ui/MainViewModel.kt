package com.armboldmind.grandmarket.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.armboldmind.grandmarket.GrandMarketApp
import com.armboldmind.grandmarket.base.BaseViewModel
import com.armboldmind.grandmarket.base.UIState
import com.armboldmind.grandmarket.data.IUserRepository
import com.armboldmind.grandmarket.di.Local
import com.armboldmind.grandmarket.di.Remote
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject

class MainViewModel : BaseViewModel() {

    @Inject
    lateinit var mContext: Retrofit

    @Inject
    @Local
    lateinit var mUserRepositoryLocal: IUserRepository

    @Inject
    @Remote
    lateinit var mUserRepositoryRemote: IUserRepository

    init {
        viewModelScope.launch {
            delay(3000)
            _uiState.postValue(UIState.EMPTY)
            mUserRepositoryLocal.signIn()
            mUserRepositoryRemote.signIn()
        }
        GrandMarketApp.getInstance().mAppComponent?.authorizationComponent?.build()?.inject(this)
        Log.i("Logesxnst", mContext.toString())

    }

    fun setNetworkError() {
        _uiState.value = UIState.NETWORK_ERROR
    }

    fun setServerError() {
        _uiState.value = UIState.SERVER_ERROR
    }

    fun setSuccess() {
        _uiState.value = UIState.SUCCESS
    }
}